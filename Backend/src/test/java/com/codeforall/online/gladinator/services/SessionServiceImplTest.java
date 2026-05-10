package com.codeforall.online.gladinator.services;

import static org.junit.Assert.*;

import com.codeforall.online.gladinator.exceptions.GameSessionNotFoundException;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
import com.codeforall.online.gladinator.model.session.GameConfig;
import com.codeforall.online.gladinator.model.session.GameSession;
import com.codeforall.online.gladinator.storage.InMemorySessionStore;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the session service implementation.
 */
public class SessionServiceImplTest {

  private SessionServiceImpl sessionService;

  @Before
  public void setUp() {
    sessionService = new SessionServiceImpl(new InMemorySessionStore(), new GameConfig(3, 4));
  }

  /**
   * Verifies that a new session starts with the expected default state.
   */
  @Test
  public void createSessionShouldInitializeDefaultState() {
    GameSession session = sessionService.createSession();

    assertNotNull(session.getSessionId());
    assertEquals(3, session.getRemainingLives());
    assertEquals(0, session.getQuestionCountInRound());
    assertEquals(GameStatus.IN_PROGRESS, session.getGameStatus());
    assertEquals(PersonalityType.DEFAULT, session.getPersonalityType());
    assertNotNull(session.getAnswersHistory());
    assertTrue(session.getAnswersHistory().isEmpty());
  }

  /**
   * Verifies that a missing session raises the domain-specific exception.
   */
  @Test
  public void getSessionByIdShouldThrowWhenSessionDoesNotExist() {
    assertThrows(GameSessionNotFoundException.class,
        () -> sessionService.getSessionById("missing-session"));
  }
}
