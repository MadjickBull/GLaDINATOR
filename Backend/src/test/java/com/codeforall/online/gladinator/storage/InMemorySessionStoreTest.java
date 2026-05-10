package com.codeforall.online.gladinator.storage;

import com.codeforall.online.gladinator.model.session.GameSession;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link InMemorySessionStore}.
 */
public class InMemorySessionStoreTest {

    private InMemorySessionStore sessionStore;

    @Before
    public void setUp() {
        sessionStore = new InMemorySessionStore();
    }

    /**
     * Verifies that a saved session can be retrieved by its identifier.
     */
    @Test
    public void saveShouldStoreSession() {
        GameSession session = new GameSession();
        session.setSessionId("session-1");

        sessionStore.save(session);

        assertEquals(session, sessionStore.findById("session-1"));
    }

    /**
     * Verifies that the store reports an existing session correctly.
     */
    @Test
    public void existsShouldReturnTrueWhenSessionIsStored() {
        GameSession session = new GameSession();
        session.setSessionId("session-1");
        sessionStore.save(session);

        assertTrue(sessionStore.exists("session-1"));
    }

    /**
     * Verifies that the store reports missing sessions correctly.
     */
    @Test
    public void existsShouldReturnFalseWhenSessionDoesNotExist() {
        assertFalse(sessionStore.exists("missing-session"));
    }

    /**
     * Verifies that deleting a session removes it from the store.
     */
    @Test
    public void deleteShouldRemoveStoredSession() {
        GameSession session = new GameSession();
        session.setSessionId("session-1");
        sessionStore.save(session);

        sessionStore.delete("session-1");

        assertNull(sessionStore.findById("session-1"));
    }

    /**
     * Verifies that all stored sessions are returned.
     */
    @Test
    public void findAllShouldReturnAllStoredSessions() {
        GameSession firstSession = new GameSession();
        firstSession.setSessionId("session-1");

        GameSession secondSession = new GameSession();
        secondSession.setSessionId("session-2");

        sessionStore.save(firstSession);
        sessionStore.save(secondSession);

        Collection<GameSession> sessions = sessionStore.findAll();

        assertThat(sessions, hasSize(2));
        assertThat(sessions, contains(firstSession, secondSession));
    }
}
