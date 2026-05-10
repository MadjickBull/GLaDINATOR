package com.codeforall.online.gladinator.converters;

import static org.junit.Assert.*;

import com.codeforall.online.gladinator.dtos.response.GameStateDto;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.junit.Test;

/**
 * Unit tests for the game-state converter.
 */
public class GameSessionToGameStateDtoTest {

  /**
   * Verifies that the converter maps the current session state fields.
   */
  @Test
  public void convertShouldMapGameStateFields() {
    GameSession session = new GameSession();
    session.setSessionId("session-state");
    session.setPersonalityType(PersonalityType.SARCASTIC);
    session.setRemainingLives(2);
    session.setQuestionCountInRound(3);
    session.setGameStatus(GameStatus.IN_PROGRESS);
    session.setLastQuestion("Last question");
    session.setLastAiMessage("Last message");
    session.setFinalGuess("Last guess");

    GameStateDto dto = new GameSessionToGameStateDto().convert(session);

    assertEquals("session-state", dto.getSessionId());
    assertEquals(PersonalityType.SARCASTIC, dto.getPersonalityType());
    assertEquals(2, dto.getRemainingLives());
    assertEquals(3, dto.getQuestionCountInRound());
    assertEquals(GameStatus.IN_PROGRESS, dto.getGameStatus());
    assertEquals("Last question", dto.getLastQuestion());
    assertEquals("Last message", dto.getLastAiMessage());
    assertEquals("Last guess", dto.getFinalGuess());
  }
}
