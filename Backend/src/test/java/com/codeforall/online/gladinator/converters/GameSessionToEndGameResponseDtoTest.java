package com.codeforall.online.gladinator.converters;

import static org.junit.Assert.*;

import com.codeforall.online.gladinator.dtos.response.EndGameResponseDto;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.junit.Test;

/**
 * Unit tests for the end-game converter.
 */
public class GameSessionToEndGameResponseDtoTest {

  /**
   * Verifies that the converter maps the end-game response fields.
   */
  @Test
  public void convertShouldMapSessionIdAndStatus() {
    GameSession session = new GameSession();
    session.setSessionId("session-end");
    session.setGameStatus(GameStatus.ENDED);

    EndGameResponseDto dto = new GameSessionToEndGameResponseDto().convert(session);

    assertEquals("session-end", dto.getSessionId());
    assertEquals(GameStatus.ENDED, dto.getGameStatus());
  }
}
