package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.converters.AiDecisionToNextStepResponseDto;
import com.codeforall.online.gladinator.converters.GameSessionToEndGameResponseDto;
import com.codeforall.online.gladinator.converters.GameSessionToGameStartRestartResponseDto;
import com.codeforall.online.gladinator.converters.GameSessionToGameStateDto;
import com.codeforall.online.gladinator.dtos.request.AnswerRequestDto;
import com.codeforall.online.gladinator.dtos.request.ChoosePersonalityRequestDto;
import com.codeforall.online.gladinator.dtos.response.EndGameResponseDto;
import com.codeforall.online.gladinator.dtos.response.GameStartRestartResponseDto;
import com.codeforall.online.gladinator.dtos.response.GameStateDto;
import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;
import com.codeforall.online.gladinator.exceptions.InvalidGameStateException;
import com.codeforall.online.gladinator.model.enums.AiDecisionType;
import com.codeforall.online.gladinator.model.enums.AnswerType;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
import com.codeforall.online.gladinator.model.ia.AiDecision;
import com.codeforall.online.gladinator.model.session.GameAnswer;
import com.codeforall.online.gladinator.model.session.GameConfig;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameServiceImpl implements GameService {

  private SessionService sessionService;
  private GameConfig gameConfig;
  private AiService aiService;
  private GameSessionToGameStartRestartResponseDto startRestartConverter;
  private GameSessionToGameStateDto gameStateConverter;
  private GameSessionToEndGameResponseDto endGameConverter;
  private AiDecisionToNextStepResponseDto nextStepConverter;

  /**
   * Starts a new game session.
   *
   * @return the DTO containing the initial state of the created session
   */
  @Override
  public GameStartRestartResponseDto startGame() {
    // The session starts with the default personality and can request the first AI
    // step immediately.
    GameSession session = sessionService.createSession();
    return startRestartConverter.convert(session);
  }

  /**
   * Gets the current state of a game session.
   *
   * @param sessionId the session identifier
   * @return the DTO representing the current session state
   */
  @Override
  public GameStateDto getGameState(String sessionId) {
    GameSession session = sessionService.getSessionById(sessionId);
    return gameStateConverter.convert(session);
  }

  /**
   * Chooses or updates the AI personality for the given session.
   *
   * @param sessionId the session identifier
   * @param dto       the request DTO containing the selected personality
   * @return the DTO representing the updated session state
   */
  @Override
  public GameStateDto choosePersonality(String sessionId, ChoosePersonalityRequestDto dto) {
    GameSession session = sessionService.getSessionById(sessionId);
    session.setPersonalityType(dto.getPersonalityType());
    sessionService.updateSession(session);
    return gameStateConverter.convert(session);
  }

  /**
   * Processes the user's answer for the current AI question or guess.
   *
   * @param sessionId the session identifier
   * @param dto       the request DTO containing the user's answer
   * @return the DTO representing the updated session state
   * @throws InvalidGameStateException if the answer does not match the current
   *                                   session phase
   */
  @Override
  public GameStateDto processAnswer(String sessionId, AnswerRequestDto dto) {
    GameSession session = sessionService.getSessionById(sessionId);
    validateSessionInProgress(session);

    // The round phase is decided by the question counter.
    // Before the limit the player answers a question; after the limit,
    // the player answers the AI final guess.
    if (session.getQuestionCountInRound() < gameConfig.getMaxQuestionsPerRound()) {

      if (session.getLastQuestion() == null) {
        throw new InvalidGameStateException("There is no active question to answer.");
      }

      GameAnswer answer = new GameAnswer(
          session.getLastQuestion(),
          dto.getAnswerType(),
          session.getQuestionCountInRound() + 1);

      session.getAnswersHistory().add(answer);
      session.setQuestionCountInRound(session.getQuestionCountInRound() + 1);

    } else {

      if (session.getFinalGuess() == null) {
        throw new InvalidGameStateException("There is no active guess to answer.");
      }

      if (dto.getAnswerType() == AnswerType.YES) {
        session.setGameStatus(GameStatus.AI_WON);

      } else {
        session.setRemainingLives(session.getRemainingLives() - 1);

        if (session.getRemainingLives() <= 0) {
          session.setGameStatus(GameStatus.PLAYER_WON);

        } else {
          session.setQuestionCountInRound(0);
          session.setFinalGuess(null);
          session.setLastQuestion(null);
          session.setAnswersHistory(new ArrayList<>());
        }
      }
    }

    sessionService.updateSession(session);
    return gameStateConverter.convert(session);
  }

  /**
   * Gets the next AI step for the given session.
   *
   * @param sessionId the session identifier
   * @return the DTO containing the next AI step
   */
  @Override
  public NextStepResponseDto getNextStep(String sessionId) {
    GameSession session = sessionService.getSessionById(sessionId);

    if (session.getGameStatus() == GameStatus.AI_WON ||
        session.getGameStatus() == GameStatus.PLAYER_WON) {

      AiDecision aiDecision = aiService.getNextStep(session);
      session.setLastAiMessage(aiDecision.getContent());
      sessionService.updateSession(session);
      return nextStepConverter.convert(aiDecision);
    }

    validateSessionInProgress(session);

    AiDecision aiDecision = aiService.getNextStep(session);

    if (aiDecision.getAiDecisionType() == AiDecisionType.QUESTION) {
      session.setLastQuestion(aiDecision.getContent());
      session.setLastAiMessage(aiDecision.getContent());

    } else if (aiDecision.getAiDecisionType() == AiDecisionType.GUESS) {
      session.setFinalGuess(aiDecision.getContent());
      session.setLastAiMessage(aiDecision.getContent());

    } else {
      session.setLastAiMessage(aiDecision.getContent());
    }

    sessionService.updateSession(session);
    return nextStepConverter.convert(aiDecision);
  }

  /**
   * Restarts an existing game session.
   *
   * @param sessionId the session identifier
   * @return the DTO containing the reset session state
   */
  @Override
  public GameStartRestartResponseDto restartGame(String sessionId) {
    GameSession session = sessionService.getSessionById(sessionId);

    session.setRemainingLives(gameConfig.getInitialLives());
    session.setQuestionCountInRound(0);
    session.setGameStatus(GameStatus.IN_PROGRESS);
    session.setAnswersHistory(new ArrayList<>());
    session.setLastQuestion(null);
    session.setLastAiMessage(null);
    session.setFinalGuess(null);
    session.setPersonalityType(PersonalityType.DEFAULT);

    sessionService.updateSession(session);
    return startRestartConverter.convert(session);
  }

  /**
   * Ends a game session manually.
   *
   * @param sessionId the session identifier
   * @return the DTO containing the final ended state
   */
  @Override
  public EndGameResponseDto endGame(String sessionId) {
    GameSession session = sessionService.getSessionById(sessionId);
    session.setGameStatus(GameStatus.ENDED);
    sessionService.updateSession(session);

    EndGameResponseDto endGameResponseDto = endGameConverter.convert(session);
    sessionService.deleteSessionById(sessionId);

    return endGameResponseDto;
  }

  private void validateSessionInProgress(GameSession session) {
    if (session.getGameStatus() != GameStatus.IN_PROGRESS) {
      throw new InvalidGameStateException("Game session is not in progress.");
    }
  }

  @Autowired
  public void setSessionService(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @Autowired
  public void setGameConfig(GameConfig gameConfig) {
    this.gameConfig = gameConfig;
  }

  @Autowired
  public void setStartRestartConverter(GameSessionToGameStartRestartResponseDto startRestartConverter) {
    this.startRestartConverter = startRestartConverter;
  }

  @Autowired
  public void setGameStateConverter(GameSessionToGameStateDto gameStateConverter) {
    this.gameStateConverter = gameStateConverter;
  }

  @Autowired
  public void setEndGameConverter(GameSessionToEndGameResponseDto endGameConverter) {
    this.endGameConverter = endGameConverter;
  }

  @Autowired
  public void setNextStepConverter(AiDecisionToNextStepResponseDto nextStepConverter) {
    this.nextStepConverter = nextStepConverter;
  }

  @Autowired
  public void setAiService(AiService aiService) {
    this.aiService = aiService;
  }
}
