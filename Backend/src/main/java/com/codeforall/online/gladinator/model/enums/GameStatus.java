package com.codeforall.online.gladinator.model.enums;

/**
 * Defines the lifecycle status of a game session.
 */
public enum GameStatus {
    IN_PROGRESS,
    AI_WON,
    PLAYER_WON,
    ENDED // The session was manually closed without a normal win condition.
}
