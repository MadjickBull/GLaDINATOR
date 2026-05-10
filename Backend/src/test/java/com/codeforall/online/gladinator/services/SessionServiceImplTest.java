package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.exceptions.GameSessionNotFoundException;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.session.GameConfig;
import com.codeforall.online.gladinator.model.session.GameSession;
import com.codeforall.online.gladinator.storage.InMemorySessionStore;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link SessionServiceImpl}.
 */
public class SessionServiceImplTest {

    private InMemorySessionStore sessionStore;
    private SessionServiceImpl sessionService;

    @Before
    public void setUp() {
        sessionStore = new InMemorySessionStore();
        sessionService = new SessionServiceImpl(sessionStore, new GameConfig(3, 4));
    }

    /**
     * Verifies that a new session starts with the expected default state.
     */
    @Test
    public void createSessionShouldInitializeSessionWithDefaultState() {
        GameSession session = sessionService.createSession();

        assertNotNull(session.getSessionId());
        assertEquals(3, session.getRemainingLives());
        assertEquals(0, session.getQuestionCountInRound());
        assertEquals(GameStatus.IN_PROGRESS, session.getGameStatus());
        assertNull(session.getPersonalityType());
        assertNotNull(session.getAnswersHistory());
        assertTrue(session.getAnswersHistory().isEmpty());
        assertEquals(session, sessionStore.findById(session.getSessionId()));
    }

    /**
     * Verifies that an existing session can be retrieved by id.
     */
    @Test
    public void getSessionByIdShouldReturnStoredSession() {
        GameSession session = sessionService.createSession();

        GameSession storedSession = sessionService.getSessionById(session.getSessionId());

        assertEquals(session, storedSession);
    }

    /**
     * Verifies that a missing session triggers the expected exception.
     */
    @Test
    public void getSessionByIdShouldThrowWhenSessionDoesNotExist() {
        assertThrows(GameSessionNotFoundException.class,
                () -> sessionService.getSessionById("missing-session"));
    }

    /**
     * Verifies that updates to a session are persisted in the store.
     */
    @Test
    public void updateSessionShouldPersistSessionChanges() {
        GameSession session = sessionService.createSession();
        session.setLastQuestion("Is your character human?");

        sessionService.updateSession(session);

        assertEquals("Is your character human?",
                sessionService.getSessionById(session.getSessionId()).getLastQuestion());
    }

    /**
     * Verifies that deleting a session removes it from the service store.
     */
    @Test
    public void deleteSessionByIdShouldRemoveStoredSession() {
        GameSession session = sessionService.createSession();

        sessionService.deleteSessionById(session.getSessionId());

        assertThrows(GameSessionNotFoundException.class,
                () -> sessionService.getSessionById(session.getSessionId()));
    }

    /**
     * Verifies that the existence check reflects the current store state.
     */
    @Test
    public void sessionExistsShouldReflectStoredState() {
        GameSession session = sessionService.createSession();

        assertTrue(sessionService.sessionExists(session.getSessionId()));
    }
}
