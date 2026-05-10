package com.codeforall.online.gladinator.services;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.codeforall.online.gladinator.exceptions.AiIntegrationException;
import com.codeforall.online.gladinator.model.enums.AiDecisionType;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
import com.codeforall.online.gladinator.model.ia.AiDecision;
import com.codeforall.online.gladinator.model.session.GameConfig;
import com.codeforall.online.gladinator.model.session.GameSession;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit tests for the AI service implementation.
 */
public class AiServiceImplTest {

  private AiServiceImpl aiService;
  private ChatClient chatClient;

  @Before
  public void setUp() {
    aiService = new AiServiceImpl();
    chatClient = mock(ChatClient.class, Answers.RETURNS_DEEP_STUBS);
    aiService.setGameConfig(new GameConfig(3, 4));
    aiService.setChatClient(chatClient);
    ReflectionTestUtils.setField(aiService, "gamePromptTemplate",
        new ByteArrayResource("Prompt: <personalityDescription>".getBytes(StandardCharsets.UTF_8)));
  }

  private GameSession buildSession() {
    GameSession session = new GameSession();
    session.setGameStatus(GameStatus.IN_PROGRESS);
    session.setPersonalityType(PersonalityType.DEFAULT);
    session.setRemainingLives(3);
    session.setQuestionCountInRound(0);
    session.setAnswersHistory(new ArrayList<>());
    return session;
  }

  /**
   * Verifies that the service returns a final message when the player has already won.
   */
  @Test
  public void getNextStepShouldReturnFinalMessageWhenPlayerWon() {
    GameSession session = buildSession();
    session.setGameStatus(GameStatus.PLAYER_WON);

    AiDecision result = aiService.getNextStep(session);

    assertEquals(AiDecisionType.FINAL_MESSAGE, result.getAiDecisionType());
    assertNotNull(result.getContent());
  }

  /**
   * Verifies that the service returns a question while the round is still below the limit.
   */
  @Test
  public void getNextStepShouldReturnQuestionBeforeGuessPhase() {
    GameSession session = buildSession();
    when(chatClient.prompt().user(anyString()).call().content()).thenReturn("Is your character human?");

    AiDecision result = aiService.getNextStep(session);

    assertEquals(AiDecisionType.QUESTION, result.getAiDecisionType());
    assertEquals("Is your character human?", result.getContent());
  }

  /**
   * Verifies that the service returns a guess once the question limit has been reached.
   */
  @Test
  public void getNextStepShouldReturnGuessAtQuestionLimit() {
    GameSession session = buildSession();
    session.setQuestionCountInRound(4);
    when(chatClient.prompt().user(anyString()).call().content()).thenReturn("My guess is: Batman.");

    AiDecision result = aiService.getNextStep(session);

    assertEquals(AiDecisionType.GUESS, result.getAiDecisionType());
    assertEquals("My guess is: Batman.", result.getContent());
  }

  /**
   * Verifies that integration failures are wrapped in the domain exception.
   */
  @Test
  public void getNextStepShouldWrapChatClientErrors() {
    GameSession session = buildSession();
    when(chatClient.prompt().user(anyString()).call().content()).thenThrow(new RuntimeException("boom"));

    assertThrows(AiIntegrationException.class, () -> aiService.getNextStep(session));
  }
}
