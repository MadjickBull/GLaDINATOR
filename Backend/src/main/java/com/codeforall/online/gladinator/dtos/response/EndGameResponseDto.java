package com.codeforall.online.gladinator.dtos.response;

import com.codeforall.online.gladinator.model.enums.GameStatus;

/**
 * Response DTO returned when a game session is ended manually.
 */
public class EndGameResponseDto {

    private String sessionId;
    private GameStatus gameStatus;

    public EndGameResponseDto() {
    }

    public EndGameResponseDto(String sessionId, GameStatus gameStatus) {
        this.sessionId = sessionId;
        this.gameStatus = gameStatus;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
