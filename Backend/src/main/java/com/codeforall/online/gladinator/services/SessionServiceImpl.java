package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
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
        this.gameConfig =  gameConfig;
    }

    @Override
    public GameSession createSession() {

        String sessionId = generateUniqueSessionId();

        GameSession gameSession = new GameSession();
        gameSession.setSessionId(sessionId);
        gameSession.setRemainingLives(gameConfig.getInitialLives());
        gameSession.setQuestionCountInRound(0);
        gameSession.setGameStatus(GameStatus.IN_PROGRESS);
        gameSession.setPersonalityType(PersonalityType.DEFAULT);
        gameSession.setLastQuestion(null);
        gameSession.setLastAiMessage(null);
        gameSession.setAnswersHistory(new ArrayList<>());
        gameSession.setFinalGuess(null);

        sessionStore.save(gameSession);

        return gameSession;
    }

    @Override
    public GameSession getSessionById(String sessionId) {
        GameSession gameSession = sessionStore.findById(sessionId);

        if (gameSession == null) {
            throw new IllegalStateException("Game session " + sessionId + " not found.");
        }

        return gameSession;
    }

    @Override
    public void updateSession(GameSession gameSession) {
        sessionStore.save(gameSession);
    }

    @Override
    public void deleteSessionById(String sessionId) {
        sessionStore.delete(sessionId);
    }

    @Override
    public boolean sessionExists(String sessionId) {
        return sessionStore.exists(sessionId);
    }

    //se já existe um UUID gera outro
    private String generateUniqueSessionId() {
        String sessionId = UUID.randomUUID().toString();

        while (sessionStore.exists(sessionId)) {
            sessionId = UUID.randomUUID().toString();
        }

        return sessionId;
    }
}