package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.exceptions.AiIntegrationException;
import com.codeforall.online.gladinator.model.enums.AiDecisionType;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
import com.codeforall.online.gladinator.model.ia.AiDecision;
import com.codeforall.online.gladinator.model.session.GameAnswer;
import com.codeforall.online.gladinator.model.session.GameConfig;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link AiServiceImpl}.
 */
public class AiServiceImplTest {

    private AiServiceImpl aiService;
    private ChatClient chatClient;

    @Before
    public void setUp() {
        aiService = new AiServiceImpl();
        aiService.setGameConfig(new GameConfig(3, 4));
        chatClient = mock(ChatClient.class, RETURNS_DEEP_STUBS);
        aiService.setChatClient(chatClient);

        ByteArrayResource promptResource = new ByteArrayResource((
                "Persona: {personalityDescription}\n" +
                "Status: {gameStatus}\n" +
                "Lives: {remainingLives}\n" +
                "Questions: {questionCount}/{maxQuestions}\n" +
                "History: {history}"
        ).getBytes(StandardCharsets.UTF_8));

        ReflectionTestUtils.setField(aiService, "gamePromptTemplate", promptResource);
    }

    /**
     * Verifies that a player win immediately returns the fixed final AI message.
     */
    @Test
    public void getNextStepShouldReturnFinalMessageWhenPlayerWon() {
        GameSession session = createSession();
        session.setGameStatus(GameStatus.PLAYER_WON);

        AiDecision result = aiService.getNextStep(session);

        assertEquals(AiDecisionType.FINAL_MESSAGE, result.getAiDecisionType());
        assertEquals("You win. I was clearly sabotaged.", result.getContent());
    }

    /**
     * Verifies that an AI win immediately returns the fixed victory message.
     */
    @Test
    public void getNextStepShouldReturnFinalMessageWhenAiWon() {
        GameSession session = createSession();
        session.setGameStatus(GameStatus.AI_WON);

        AiDecision result = aiService.getNextStep(session);

        assertEquals(AiDecisionType.FINAL_MESSAGE, result.getAiDecisionType());
        assertEquals("I knew it all along. Too easy.", result.getContent());
    }

    /**
     * Verifies that the AI returns a question before the round limit is reached.
     */
    @Test
    public void getNextStepShouldReturnQuestionBeforeRoundLimit() {
        GameSession session = createSession();
        when(chatClient.prompt().user(anyString()).call().content()).thenReturn("Is your character human?");

        AiDecision result = aiService.getNextStep(session);

        assertEquals(AiDecisionType.QUESTION, result.getAiDecisionType());
        assertEquals("Is your character human?", result.getContent());
    }

    /**
     * Verifies that the AI returns a guess once the round limit has been reached.
     */
    @Test
    public void getNextStepShouldReturnGuessWhenRoundLimitIsReached() {
        GameSession session = createSession();
        session.setQuestionCountInRound(4);
        when(chatClient.prompt().user(anyString()).call().content()).thenReturn("Batman");

        AiDecision result = aiService.getNextStep(session);

        assertEquals(AiDecisionType.GUESS, result.getAiDecisionType());
        assertEquals("Batman", result.getContent());
    }

    /**
     * Verifies that integration failures are wrapped in the domain-specific exception.
     */
    @Test
    public void getNextStepShouldWrapIntegrationFailures() {
        GameSession session = createSession();
        when(chatClient.prompt().user(anyString()).call().content()).thenThrow(new RuntimeException("boom"));

        assertThrows(AiIntegrationException.class, () -> aiService.getNextStep(session));
    }

    /**
     * Verifies that history can be included in the generated prompt without breaking the flow.
     */
    @Test
    public void getNextStepShouldBuildPromptWithHistoryWithoutFailing() {
        GameSession session = createSession();
        session.getAnswersHistory().add(new GameAnswer("Is your character human?", com.codeforall.online.gladinator.model.enums.AnswerType.YES, 1));
        when(chatClient.prompt().user(anyString()).call().content()).thenReturn("Is your character fictional?");

        AiDecision result = aiService.getNextStep(session);

        assertEquals(AiDecisionType.QUESTION, result.getAiDecisionType());
        assertEquals("Is your character fictional?", result.getContent());
    }

    private GameSession createSession() {
        GameSession session = new GameSession();
        session.setSessionId("session-1");
        session.setGameStatus(GameStatus.IN_PROGRESS);
        session.setPersonalityType(PersonalityType.DEFAULT);
        session.setRemainingLives(3);
        session.setQuestionCountInRound(0);
        session.setAnswersHistory(new ArrayList<>());
        return session;
    }
}
