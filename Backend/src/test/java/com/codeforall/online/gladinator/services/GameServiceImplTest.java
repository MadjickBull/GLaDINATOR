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
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link GameServiceImpl}.
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
     * Verifies that starting a game creates a session and converts it to the response DTO.
     */
    @Test
    public void startGameShouldCreateAndConvertNewSession() {
        GameSession session = createSession();
        GameStartRestartResponseDto responseDto = new GameStartRestartResponseDto();

        when(sessionService.createSession()).thenReturn(session);
        when(startRestartConverter.convert(session)).thenReturn(responseDto);

        GameStartRestartResponseDto result = gameService.startGame();

        assertEquals(responseDto, result);
        verify(sessionService).createSession();
        verify(startRestartConverter).convert(session);
    }

    /**
     * Verifies that choosing a personality updates the session and returns the mapped state.
     */
    @Test
    public void choosePersonalityShouldUpdateSessionPersonalityAndReturnConvertedState() {
        GameSession session = createSession();
        GameStateDto responseDto = new GameStateDto();
        ChoosePersonalityRequestDto requestDto = new ChoosePersonalityRequestDto(PersonalityType.SARCASTIC);

        when(sessionService.getSessionById("session-1")).thenReturn(session);
        when(gameStateConverter.convert(session)).thenReturn(responseDto);

        GameStateDto result = gameService.choosePersonality("session-1", requestDto);

        assertEquals(PersonalityType.SARCASTIC, session.getPersonalityType());
        assertEquals(responseDto, result);
        verify(sessionService).updateSession(session);
    }

    /**
     * Verifies that answering a normal AI question stores the answer and increments the round counter.
     */
    @Test
    public void processAnswerShouldStoreAnswerAndIncrementQuestionCounterDuringQuestionPhase() {
        GameSession session = createSession();
        session.setLastQuestion("Is your character human?");
        GameStateDto responseDto = new GameStateDto();

        when(sessionService.getSessionById("session-1")).thenReturn(session);
        when(gameStateConverter.convert(session)).thenReturn(responseDto);

        GameStateDto result = gameService.processAnswer("session-1", new AnswerRequestDto(AnswerType.YES));

        assertEquals(responseDto, result);
        assertEquals(1, session.getQuestionCountInRound());
        assertThat(session.getAnswersHistory(), hasSize(1));
        GameAnswer answer = session.getAnswersHistory().get(0);
        assertEquals("Is your character human?", answer.getQuestion());
        assertEquals(AnswerType.YES, answer.getAnswerType());
        assertEquals(1, answer.getQuestionOrder());
        verify(sessionService).updateSession(session);
    }

    /**
     * Verifies that answers are rejected when the game is no longer in progress.
     */
    @Test
    public void processAnswerShouldThrowWhenGameIsNotInProgress() {
        GameSession session = createSession();
        session.setGameStatus(GameStatus.ENDED);

        when(sessionService.getSessionById("session-1")).thenReturn(session);

        assertThrows(InvalidGameStateException.class,
                () -> gameService.processAnswer("session-1", new AnswerRequestDto(AnswerType.YES)));
    }

    /**
     * Verifies that the service rejects a question answer when there is no active question.
     */
    @Test
    public void processAnswerShouldThrowWhenThereIsNoActiveQuestion() {
        GameSession session = createSession();

        when(sessionService.getSessionById("session-1")).thenReturn(session);

        assertThrows(InvalidGameStateException.class,
                () -> gameService.processAnswer("session-1", new AnswerRequestDto(AnswerType.YES)));
    }

    /**
     * Verifies that confirming the AI guess marks the AI as the winner.
     */
    @Test
    public void processAnswerShouldSetAiWonWhenGuessIsConfirmed() {
        GameSession session = createSession();
        session.setQuestionCountInRound(4);
        session.setFinalGuess("Batman");
        GameStateDto responseDto = new GameStateDto();

        when(sessionService.getSessionById("session-1")).thenReturn(session);
        when(gameStateConverter.convert(session)).thenReturn(responseDto);

        GameStateDto result = gameService.processAnswer("session-1", new AnswerRequestDto(AnswerType.YES));

        assertEquals(responseDto, result);
        assertEquals(GameStatus.AI_WON, session.getGameStatus());
    }

    /**
     * Verifies that a wrong guess removes one life and resets the round when lives remain.
     */
    @Test
    public void processAnswerShouldDecreaseLivesAndResetRoundWhenGuessIsWrongButLivesRemain() {
        GameSession session = createSession();
        session.setQuestionCountInRound(4);
        session.setFinalGuess("Batman");
        session.setLastQuestion("Is your character human?");
        session.setRemainingLives(3);
        session.setAnswersHistory(new ArrayList<>());
        session.getAnswersHistory().add(new GameAnswer("Is your character human?", AnswerType.YES, 1));
        GameStateDto responseDto = new GameStateDto();

        when(sessionService.getSessionById("session-1")).thenReturn(session);
        when(gameStateConverter.convert(session)).thenReturn(responseDto);

        gameService.processAnswer("session-1", new AnswerRequestDto(AnswerType.NO));

        assertEquals(2, session.getRemainingLives());
        assertEquals(0, session.getQuestionCountInRound());
        assertNull(session.getFinalGuess());
        assertNull(session.getLastQuestion());
        assertThat(session.getAnswersHistory(), hasSize(0));
    }

    /**
     * Verifies that a wrong guess with no lives remaining makes the player win.
     */
    @Test
    public void processAnswerShouldSetPlayerWonWhenGuessIsWrongAndLivesRunOut() {
        GameSession session = createSession();
        session.setQuestionCountInRound(4);
        session.setFinalGuess("Batman");
        session.setRemainingLives(1);
        GameStateDto responseDto = new GameStateDto();

        when(sessionService.getSessionById("session-1")).thenReturn(session);
        when(gameStateConverter.convert(session)).thenReturn(responseDto);

        gameService.processAnswer("session-1", new AnswerRequestDto(AnswerType.NO));

        assertEquals(0, session.getRemainingLives());
        assertEquals(GameStatus.PLAYER_WON, session.getGameStatus());
    }

    /**
     * Verifies that the next AI step cannot be requested before a personality is selected.
     */
    @Test
    public void getNextStepShouldThrowWhenPersonalityWasNotSelected() {
        GameSession session = createSession();
        session.setPersonalityType(null);

        when(sessionService.getSessionById("session-1")).thenReturn(session);

        assertThrows(InvalidGameStateException.class,
                () -> gameService.getNextStep("session-1"));
    }

    /**
     * Verifies that AI questions are stored as both last question and last AI message.
     */
    @Test
    public void getNextStepShouldStoreLastQuestionWhenAiReturnsQuestion() {
        GameSession session = createSession();
        session.setPersonalityType(PersonalityType.DEFAULT);
        AiDecision decision = new AiDecision(AiDecisionType.QUESTION, "Is your character human?");
        NextStepResponseDto responseDto = new NextStepResponseDto();

        when(sessionService.getSessionById("session-1")).thenReturn(session);
        when(aiService.getNextStep(session)).thenReturn(decision);
        when(nextStepConverter.convert(decision)).thenReturn(responseDto);

        NextStepResponseDto result = gameService.getNextStep("session-1");

        assertEquals(responseDto, result);
        assertEquals("Is your character human?", session.getLastQuestion());
        assertEquals("Is your character human?", session.getLastAiMessage());
        verify(sessionService).updateSession(session);
    }

    /**
     * Verifies that AI guesses are stored as the final guess and last AI message.
     */
    @Test
    public void getNextStepShouldStoreFinalGuessWhenAiReturnsGuess() {
        GameSession session = createSession();
        session.setPersonalityType(PersonalityType.DEFAULT);
        AiDecision decision = new AiDecision(AiDecisionType.GUESS, "Batman");
        NextStepResponseDto responseDto = new NextStepResponseDto();

        when(sessionService.getSessionById("session-1")).thenReturn(session);
        when(aiService.getNextStep(session)).thenReturn(decision);
        when(nextStepConverter.convert(decision)).thenReturn(responseDto);

        NextStepResponseDto result = gameService.getNextStep("session-1");

        assertEquals(responseDto, result);
        assertEquals("Batman", session.getFinalGuess());
        assertEquals("Batman", session.getLastAiMessage());
    }

    /**
     * Verifies that finished sessions return a final AI message instead of a normal turn.
     */
    @Test
    public void getNextStepShouldReturnFinalMessageForFinishedGameStates() {
        GameSession session = createSession();
        session.setGameStatus(GameStatus.AI_WON);
        AiDecision decision = new AiDecision(AiDecisionType.FINAL_MESSAGE, "Too easy.");
        NextStepResponseDto responseDto = new NextStepResponseDto();

        when(sessionService.getSessionById("session-1")).thenReturn(session);
        when(aiService.getNextStep(session)).thenReturn(decision);
        when(nextStepConverter.convert(decision)).thenReturn(responseDto);

        NextStepResponseDto result = gameService.getNextStep("session-1");

        assertEquals(responseDto, result);
        assertEquals("Too easy.", session.getLastAiMessage());
    }

    /**
     * Verifies that restarting a session resets all mutable game state.
     */
    @Test
    public void restartGameShouldResetMutableSessionState() {
        GameSession session = createSession();
        session.setPersonalityType(PersonalityType.LOVER);
        session.setRemainingLives(1);
        session.setQuestionCountInRound(3);
        session.setLastQuestion("Question");
        session.setLastAiMessage("Message");
        session.setFinalGuess("Batman");
        session.getAnswersHistory().add(new GameAnswer("Question", AnswerType.YES, 1));
        GameStartRestartResponseDto responseDto = new GameStartRestartResponseDto();

        when(sessionService.getSessionById("session-1")).thenReturn(session);
        when(startRestartConverter.convert(session)).thenReturn(responseDto);

        GameStartRestartResponseDto result = gameService.restartGame("session-1");

        assertEquals(responseDto, result);
        assertEquals(3, session.getRemainingLives());
        assertEquals(0, session.getQuestionCountInRound());
        assertEquals(GameStatus.IN_PROGRESS, session.getGameStatus());
        assertNull(session.getPersonalityType());
        assertNull(session.getLastQuestion());
        assertNull(session.getLastAiMessage());
        assertNull(session.getFinalGuess());
        assertThat(session.getAnswersHistory(), hasSize(0));
    }

    /**
     * Verifies that ending a session marks it as ended, converts it, and deletes it.
     */
    @Test
    public void endGameShouldMarkSessionAsEndedConvertItAndDeleteIt() {
        GameSession session = createSession();
        EndGameResponseDto responseDto = new EndGameResponseDto();

        when(sessionService.getSessionById("session-1")).thenReturn(session);
        when(endGameConverter.convert(session)).thenReturn(responseDto);

        EndGameResponseDto result = gameService.endGame("session-1");

        assertEquals(responseDto, result);
        assertEquals(GameStatus.ENDED, session.getGameStatus());
        verify(sessionService).updateSession(session);
        verify(sessionService).deleteSessionById("session-1");
    }

    private GameSession createSession() {
        GameSession session = new GameSession();
        session.setSessionId("session-1");
        session.setGameStatus(GameStatus.IN_PROGRESS);
        session.setRemainingLives(3);
        session.setQuestionCountInRound(0);
        session.setAnswersHistory(new ArrayList<>());
        return session;
    }
}
