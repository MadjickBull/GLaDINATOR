package com.codeforall.online.gladinator.model.session;

import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;

import java.util.List;

/**
 * Represents the complete mutable state of a game session.
 */
public class GameSession {

    // Unique identifier of the session.
    private String sessionId;
    // Current AI personality selected for this session.
    private PersonalityType personalityType;
    private int remainingLives;
    // Number of questions already asked in the current round.
    private int questionCountInRound;
    private GameStatus gameStatus;
    private String lastQuestion;
    private String lastAiMessage;
    private String finalGuess;
    // Session question-and-answer history used by the backend and AI as context.
    private List<GameAnswer> answersHistory;

    public GameSession() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public PersonalityType getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(PersonalityType personalityType) {
        this.personalityType = personalityType;
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    public int getQuestionCountInRound() {
        return questionCountInRound;
    }

    public void setQuestionCountInRound(int questionCountInRound) {
        this.questionCountInRound = questionCountInRound;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getLastQuestion() {
        return lastQuestion;
    }

    public void setLastQuestion(String lastQuestion) {
        this.lastQuestion = lastQuestion;
    }

    public String getLastAiMessage() {
        return lastAiMessage;
    }

    public void setLastAiMessage(String lastAiMessage) {
        this.lastAiMessage = lastAiMessage;
    }

    public String getFinalGuess() {
        return finalGuess;
    }

    public void setFinalGuess(String finalGuess) {
        this.finalGuess = finalGuess;
    }

    public List<GameAnswer> getAnswersHistory() {
        return answersHistory;
    }

    public void setAnswersHistory(List<GameAnswer> answersHistory) {
        this.answersHistory = answersHistory;
    }
}
