package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.model.enums.AiDecisionType;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.ia.AiDecision;
import com.codeforall.online.gladinator.model.session.GameAnswer;
import com.codeforall.online.gladinator.model.session.GameConfig;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {

  private GameConfig gameConfig;
  private ChatClient chatClient;

  @Value("classpath:ai/prompts/game-prompt.st")
  private Resource gamePromptTemplate;

  @Override
  public AiDecision getNextStep(GameSession gameSession) {
    if (gameSession.getGameStatus() == GameStatus.PLAYER_WON) {
      return new AiDecision(AiDecisionType.FINAL_MESSAGE, "You win. I was clearly sabotaged.");
    }
    if (gameSession.getGameStatus() == GameStatus.AI_WON) {
      return new AiDecision(AiDecisionType.FINAL_MESSAGE, "I knew it all along. Too easy.");
    }
    String prompt = buildPrompt(gameSession);
    String content = chatClient.prompt()
        .user(prompt)
        .call()
        .content();
    String lower = content.toLowerCase();
    if (lower.startsWith("my guess is:") || lower.contains("\nmy guess is:")) {
      return new AiDecision(AiDecisionType.GUESS, content);
    }
    return new AiDecision(AiDecisionType.QUESTION, content);
  }

  private String buildPrompt(GameSession gameSession) {
    PromptTemplate promptTemplate = new PromptTemplate(gamePromptTemplate);
    return promptTemplate.create(Map.of(
        "personalityDescription", gameSession.getPersonalityType().getDescription(),
        "gameStatus", gameSession.getGameStatus().name(),
        "remainingLives", gameSession.getRemainingLives(),
        "questionCount", gameSession.getQuestionCountInRound(),
        "maxQuestions", gameConfig.getMaxQuestionsPerRound(),
        "history", buildHistory(gameSession))).getContents();
  }

  private String buildHistory(GameSession gameSession) {
    List<GameAnswer> history = gameSession.getAnswersHistory();

    if (history == null || history.isEmpty()) {
      return "No previous questions or answers.";
    }

    StringBuilder promptedMemory = new StringBuilder();
    for (GameAnswer answer : history) {
      promptedMemory.append("Question ").append(answer.getQuestionOrder())
          .append(": ").append(answer.getQuestion())
          .append(" | Answer: ").append(answer.getAnswerType().name())
          .append("\n");
    }

    return promptedMemory.toString();
  }

  @Autowired
  public void setGameConfig(GameConfig gameConfig) {
    this.gameConfig = gameConfig;
  }

  @Autowired
  public void setChatClient(ChatClient chatClient) {
    this.chatClient = chatClient;
  }
}
