package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.model.session.GameSession;

/**
 * Common interface for session services, provides methods to manage
 * the technical lifecycle of game sessions.
 */
public interface SessionService {

    /**
     * Creates a new game session.
     *
     * @return the newly created game session
     */
    GameSession createSession();

    /**
     * Gets a game session by its identifier.
     *
     * @param sessionId the session identifier
     * @return the corresponding game session
     */
    GameSession getSessionById(String sessionId);

    /**
     * Updates a game session in the underlying store.
     *
     * @param gameSession the game session to update
     */
    void updateSession(GameSession gameSession);

    /**
     * Deletes a game session by its identifier.
     *
     * @param sessionId the session identifier
     */
    void deleteSessionById(String sessionId);

    /**
     * Checks whether a game session exists.
     *
     * @param sessionId the session identifier
     * @return true if the session exists, false otherwise
     */
    boolean sessionExists(String sessionId);
}
