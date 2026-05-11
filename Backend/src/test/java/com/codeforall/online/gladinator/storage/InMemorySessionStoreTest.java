package com.codeforall.online.gladinator.storage;

import static org.junit.Assert.*;

import com.codeforall.online.gladinator.model.session.GameSession;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the in-memory session store.
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
  public void saveShouldStoreSessionById() {
    GameSession session = new GameSession();
    session.setSessionId("session-1");

    sessionStore.save(session);

    assertSame(session, sessionStore.findById("session-1"));
  }

  /**
   * Verifies that the store reports whether a session exists.
   */
  @Test
  public void existsShouldReflectStoredSessions() {
    GameSession session = new GameSession();
    session.setSessionId("session-2");
    sessionStore.save(session);

    assertTrue(sessionStore.exists("session-2"));
    assertFalse(sessionStore.exists("missing"));
  }

  /**
   * Verifies that deleting a session removes it from the store.
   */
  @Test
  public void deleteShouldRemoveStoredSession() {
    GameSession session = new GameSession();
    session.setSessionId("session-3");
    sessionStore.save(session);

    sessionStore.delete("session-3");

    assertNull(sessionStore.findById("session-3"));
  }
}
