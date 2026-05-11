package com.codeforall.online.gladinator.converters;

import static org.junit.Assert.*;

import com.codeforall.online.gladinator.dtos.response.GameStartRestartResponseDto;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.junit.Test;

/**
 * Unit tests for the start/restart converter.
 */
public class GameSessionToGameStartRestartResponseDtoTest {

  /**
   * Verifies that the converter maps the start and restart response fields.
   */
  @Test
  public void convertShouldMapStartRestartFields() {
    GameSession session = new GameSession();
    session.setSessionId("session-start");
    session.setPersonalityType(PersonalityType.DEFAULT);
    session.setRemainingLives(3);
    session.setQuestionCountInRound(0);
    session.setGameStatus(GameStatus.IN_PROGRESS);
    session.setLastAiMessage("Welcome.");

    GameStartRestartResponseDto dto = new GameSessionToGameStartRestartResponseDto().convert(session);

    assertEquals("session-start", dto.getSessionId());
    assertEquals(PersonalityType.DEFAULT, dto.getPersonalityType());
    assertEquals(3, dto.getRemainingLives());
    assertEquals(0, dto.getQuestionCountInRound());
    assertEquals(GameStatus.IN_PROGRESS, dto.getGameStatus());
    assertEquals("Welcome.", dto.getLastAiMessage());
  }
}
