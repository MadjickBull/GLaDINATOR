package com.codeforall.online.gladinator.dtos.response;

import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;

/**
 * Response DTO returned by the start and restart endpoints.
 */
public class GameStartRestartResponseDto {

    private String sessionId;
    private PersonalityType personalityType;
    private int remainingLives;
    private int questionCountInRound;
    private GameStatus gameStatus;
    private String lastAiMessage;

    public GameStartRestartResponseDto() {
    }

    public GameStartRestartResponseDto(String sessionId, PersonalityType personalityType,
                                       int remainingLives, int questionCountInRound,
                                       GameStatus gameStatus, String lastAiMessage) {
        this.sessionId = sessionId;
        this.personalityType = personalityType;
        this.remainingLives = remainingLives;
        this.questionCountInRound = questionCountInRound;
        this.gameStatus = gameStatus;
        this.lastAiMessage = lastAiMessage;
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

    public String getLastAiMessage() {
        return lastAiMessage;
    }

    public void setLastAiMessage(String lastAiMessage) {
        this.lastAiMessage = lastAiMessage;
    }
}
