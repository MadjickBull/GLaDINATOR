package com.codeforall.online.gladinator.converters;

import com.codeforall.online.gladinator.dtos.response.EndGameResponseDto;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link GameSessionToEndGameResponseDto}.
 */
public class GameSessionToEndGameResponseDtoTest {

    private GameSessionToEndGameResponseDto converter;

    @Before
    public void setUp() {
        converter = new GameSessionToEndGameResponseDto();
    }

    /**
     * Verifies that the end-game response fields are mapped correctly.
     */
    @Test
    public void convertShouldMapEndGameFields() {
        GameSession session = new GameSession();
        session.setSessionId("session-1");
        session.setGameStatus(GameStatus.ENDED);

        EndGameResponseDto result = converter.convert(session);

        assertEquals("session-1", result.getSessionId());
        assertEquals(GameStatus.ENDED, result.getGameStatus());
    }
}
