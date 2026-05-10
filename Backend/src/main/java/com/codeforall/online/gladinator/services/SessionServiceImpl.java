package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.exceptions.GameSessionNotFoundException;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.session.GameConfig;
import com.codeforall.online.gladinator.model.session.GameSession;
import com.codeforall.online.gladinator.storage.InMemorySessionStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    private final InMemorySessionStore sessionStore;
    private final GameConfig gameConfig;

    public SessionServiceImpl(InMemorySessionStore sessionStore, GameConfig gameConfig) {
        this.sessionStore = sessionStore;
        this.gameConfig = gameConfig;
    }

    /**
     * Creates and stores a new game session with the initial game configuration.
     *
     * @return the newly created game session
     */
    @Override
    public GameSession createSession() {

        String sessionId = generateUniqueSessionId();

        GameSession gameSession = new GameSession();
        gameSession.setSessionId(sessionId);
        gameSession.setRemainingLives(gameConfig.getInitialLives());
        gameSession.setQuestionCountInRound(0);
        gameSession.setGameStatus(GameStatus.IN_PROGRESS);
        // The session starts without personality so the flow stays:
        // startGame() -> choosePersonality() -> getNextStep().
        gameSession.setPersonalityType(null);
        gameSession.setLastQuestion(null);
        gameSession.setLastAiMessage(null);
        gameSession.setAnswersHistory(new ArrayList<>());
        gameSession.setFinalGuess(null);

        sessionStore.save(gameSession);

        return gameSession;
    }

    /**
     * Gets a game session by its identifier.
     *
     * @param sessionId the session identifier
     * @return the corresponding game session
     * @throws GameSessionNotFoundException if the session does not exist
     */
    @Override
    public GameSession getSessionById(String sessionId) {
        GameSession gameSession = sessionStore.findById(sessionId);

        if (gameSession == null) {
            throw new GameSessionNotFoundException("Game session " + sessionId + " not found.");
        }

        return gameSession;
    }

    /**
     * Updates a stored game session.
     *
     * @param gameSession the game session to update
     */
    @Override
    public void updateSession(GameSession gameSession) {
        sessionStore.save(gameSession);
    }

    /**
     * Deletes a stored game session by its identifier.
     *
     * @param sessionId the session identifier
     */
    @Override
    public void deleteSessionById(String sessionId) {
        sessionStore.delete(sessionId);
    }

    /**
     * Checks whether a session exists in the store.
     *
     * @param sessionId the session identifier
     * @return true if the session exists, false otherwise
     */
    @Override
    public boolean sessionExists(String sessionId) {
        return sessionStore.exists(sessionId);
    }

    // Generates a new UUID until a free session id is found.
    private String generateUniqueSessionId() {
        String sessionId = UUID.randomUUID().toString();

        while (sessionStore.exists(sessionId)) {
            sessionId = UUID.randomUUID().toString();
        }

        return sessionId;
    }
}
