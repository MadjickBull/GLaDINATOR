package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.exceptions.AiIntegrationException;
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

    /**
     * Generates the next structured AI decision based on the current session state.
     *
     * @param gameSession the current game session
     * @return the next AI decision for the session
     * @throws AiIntegrationException if the OpenAI call fails
     */

    @Override
    public AiDecision getNextStep(GameSession gameSession) {
        if (gameSession.getGameStatus() == GameStatus.PLAYER_WON) {
            return new AiDecision(AiDecisionType.FINAL_MESSAGE, "You win. I was clearly sabotaged.");
        }

        if (gameSession.getGameStatus() == GameStatus.AI_WON) {
            return new AiDecision(AiDecisionType.FINAL_MESSAGE, "I knew it all along. Too easy.");
        }

        boolean guessPhase = gameSession.getQuestionCountInRound() >= gameConfig.getMaxQuestionsPerRound();

        String prompt = buildPrompt(gameSession);
        String content = callModel(prompt);

        if (guessPhase) {
            content = ensureGuessFormat(content, gameSession);
            return new AiDecision(AiDecisionType.GUESS, content);
        }

        content = ensureQuestionFormat(content, gameSession);
        return new AiDecision(AiDecisionType.QUESTION, content);
    }

    private String callModel(String prompt) {
        try {
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            throw new AiIntegrationException("Failed to get a response from OpenAI.", e);
        }
    }

    private String ensureQuestionFormat(String content, GameSession gameSession) {
        if (isValidQuestion(content)) {
            return content;
        }

        String repairPrompt = """
            Rewrite the following response as exactly one yes/no question.
            Keep the same personality and intent.
            Do not include any explanation before or after the question.

            Original response:
            %s
            """.formatted(content);

        String repairedContent = callModel(repairPrompt);

        if (isValidQuestion(repairedContent)) {
            return repairedContent;
        }

        return "Is your character human?";
    }

    private boolean isValidQuestion(String content) {
        if (content == null) {
            return false;
        }

        String trimmed = content.trim();

        return !trimmed.isEmpty()
                && trimmed.contains("?")
                && !trimmed.startsWith("My guess is:");
    }


    private String ensureGuessFormat(String content, GameSession gameSession) {
        if (isValidGuess(content)) {
            return content;
        }

        String repairPrompt = """
            Rewrite the following response as a final guess.
            The response must begin with exactly: My guess is:
            After that, provide the character name and one short in-character remark.
            Do not ask a question.
            Do not add anything before 'My guess is:'.

            Original response:
            %s
            """.formatted(content);

        String repairedContent = callModel(repairPrompt);

        if (isValidGuess(repairedContent)) {
            return repairedContent;
        }

        return "My guess is: Sherlock Holmes. Insufferably observant, just like this exercise.";
    }

    private boolean isValidGuess(String content) {
        if (content == null) {
            return false;
        }

        return content.trim().startsWith("My guess is:");
    }


    /*
    public AiDecision getNextStep(GameSession gameSession) {
        if (gameSession.getGameStatus() == GameStatus.PLAYER_WON) {
            return new AiDecision(AiDecisionType.FINAL_MESSAGE, "You win. I was clearly sabotaged.");
        }

        if (gameSession.getGameStatus() == GameStatus.AI_WON) {
            return new AiDecision(AiDecisionType.FINAL_MESSAGE, "I knew it all along. Too easy.");
        }

        String prompt = buildPrompt(gameSession);
        String content;

        try {
            content = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            throw new AiIntegrationException("Failed to get a response from OpenAI.", e);
        }

        // The backend decides the round phase so the game rule does not depend
        // on the model writing a specific guess prefix.
        if (gameSession.getQuestionCountInRound() < gameConfig.getMaxQuestionsPerRound()) {
            return new AiDecision(AiDecisionType.QUESTION, content);
        }

        return new AiDecision(AiDecisionType.GUESS, content);
    }
    */


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
