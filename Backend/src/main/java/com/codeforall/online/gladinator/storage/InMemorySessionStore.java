package com.codeforall.online.gladinator.storage;

import com.codeforall.online.gladinator.model.session.GameSession;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores active game sessions in memory while the application is running.
 */
@Component
public class InMemorySessionStore {

    private final Map<String, GameSession> sessions;

    public InMemorySessionStore() {
        this.sessions = new HashMap<>();
    }

    /**
     * Saves a session in memory or updates it if it already exists.
     *
     * @param gameSession the session to save
     */
    public void save(GameSession gameSession) {
        sessions.put(gameSession.getSessionId(), gameSession);
    }

    /**
     * Retrieves a session by its identifier.
     *
     * @param sessionId the session identifier
     * @return the stored session, or null if it does not exist
     */
    public GameSession findById(String sessionId) {
        return sessions.get(sessionId);
    }

    /**
     * Checks whether a session exists in memory.
     *
     * @param sessionId the session identifier
     * @return true if the session exists, false otherwise
     */
    public boolean exists(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    /**
     * Removes a session from memory.
     *
     * @param sessionId the session identifier
     */
    public void delete(String sessionId) {
        sessions.remove(sessionId);
    }

    /**
     * Returns all sessions currently stored in memory.
     *
     * @return the collection of stored sessions
     */
    public Collection<GameSession> findAll() {
        return sessions.values();
    }
}
