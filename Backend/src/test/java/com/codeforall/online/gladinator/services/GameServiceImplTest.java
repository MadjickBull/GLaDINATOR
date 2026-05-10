package com.codeforall.online.gladinator.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.codeforall.online.gladinator.converters.AiDecisionToNextStepResponseDto;
import com.codeforall.online.gladinator.converters.GameSessionToEndGameResponseDto;
import com.codeforall.online.gladinator.converters.GameSessionToGameStartRestartResponseDto;
import com.codeforall.online.gladinator.converters.GameSessionToGameStateDto;
import com.codeforall.online.gladinator.dtos.response.GameStartRestartResponseDto;
import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;
import com.codeforall.online.gladinator.model.enums.AiDecisionType;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
import com.codeforall.online.gladinator.model.ia.AiDecision;
import com.codeforall.online.gladinator.model.session.GameConfig;
import com.codeforall.online.gladinator.model.session.GameSession;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the main game service implementation.
 */
public class GameServiceImplTest {

  private GameServiceImpl gameService;
  private SessionService sessionService;
  private AiService aiService;
  private GameSessionToGameStartRestartResponseDto startRestartConverter;
  private GameSessionToGameStateDto gameStateConverter;
  private GameSessionToEndGameResponseDto endGameConverter;
  private AiDecisionToNextStepResponseDto nextStepConverter;

  @Before
  public void setUp() {
    gameService = new GameServiceImpl();
    sessionService = mock(SessionService.class);
    aiService = mock(AiService.class);
    startRestartConverter = mock(GameSessionToGameStartRestartResponseDto.class);
    gameStateConverter = mock(GameSessionToGameStateDto.class);
    endGameConverter = mock(GameSessionToEndGameResponseDto.class);
    nextStepConverter = mock(AiDecisionToNextStepResponseDto.class);

    gameService.setSessionService(sessionService);
    gameService.setAiService(aiService);
    gameService.setGameConfig(new GameConfig(3, 4));
    gameService.setStartRestartConverter(startRestartConverter);
    gameService.setGameStateConverter(gameStateConverter);
    gameService.setEndGameConverter(endGameConverter);
    gameService.setNextStepConverter(nextStepConverter);
  }

  /**
   * Verifies that startGame delegates session creation and returns the converted DTO.
   */
  @Test
  public void startGameShouldReturnConvertedSession() {
    GameSession session = new GameSession();
    GameStartRestartResponseDto dto = new GameStartRestartResponseDto();
    when(sessionService.createSession()).thenReturn(session);
    when(startRestartConverter.convert(session)).thenReturn(dto);

    GameStartRestartResponseDto result = gameService.startGame();

    assertSame(dto, result);
    verify(sessionService).createSession();
    verify(startRestartConverter).convert(session);
  }

  /**
   * Verifies that the next AI question is stored in the session state.
   */
  @Test
  public void getNextStepShouldStoreQuestionWhenAiReturnsQuestion() {
    GameSession session = new GameSession();
    session.setSessionId("session-1");
    session.setGameStatus(GameStatus.IN_PROGRESS);
    session.setPersonalityType(PersonalityType.DEFAULT);
    session.setQuestionCountInRound(0);
    session.setAnswersHistory(new ArrayList<>());

    AiDecision aiDecision = new AiDecision(AiDecisionType.QUESTION, "Is your character human?");
    NextStepResponseDto dto = new NextStepResponseDto(AiDecisionType.QUESTION, "Is your character human?");

    when(sessionService.getSessionById("session-1")).thenReturn(session);
    when(aiService.getNextStep(session)).thenReturn(aiDecision);
    when(nextStepConverter.convert(aiDecision)).thenReturn(dto);

    NextStepResponseDto result = gameService.getNextStep("session-1");

    assertSame(dto, result);
    assertEquals("Is your character human?", session.getLastQuestion());
    assertEquals("Is your character human?", session.getLastAiMessage());
    verify(sessionService).updateSession(session);
  }

  /**
   * Verifies that restart resets the session state and restores the default personality.
   */
  @Test
  public void restartGameShouldResetStateAndDefaultPersonality() {
    GameSession session = new GameSession();
    session.setSessionId("session-2");
    session.setRemainingLives(1);
    session.setQuestionCountInRound(4);
    session.setGameStatus(GameStatus.AI_WON);
    session.setPersonalityType(PersonalityType.SARCASTIC);
    session.setLastQuestion("Old question");
    session.setLastAiMessage("Old message");
    session.setFinalGuess("Old guess");
    session.setAnswersHistory(new ArrayList<>());
    session.getAnswersHistory().add(null);

    GameStartRestartResponseDto dto = new GameStartRestartResponseDto();
    when(sessionService.getSessionById("session-2")).thenReturn(session);
    when(startRestartConverter.convert(session)).thenReturn(dto);

    GameStartRestartResponseDto result = gameService.restartGame("session-2");

    assertSame(dto, result);
    assertEquals(3, session.getRemainingLives());
    assertEquals(0, session.getQuestionCountInRound());
    assertEquals(GameStatus.IN_PROGRESS, session.getGameStatus());
    assertEquals(PersonalityType.DEFAULT, session.getPersonalityType());
    assertNull(session.getLastQuestion());
    assertNull(session.getLastAiMessage());
    assertNull(session.getFinalGuess());
    assertTrue(session.getAnswersHistory().isEmpty());
    verify(sessionService).updateSession(session);
  }
}
