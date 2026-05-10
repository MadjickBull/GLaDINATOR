package com.codeforall.online.gladinator.converters;

import com.codeforall.online.gladinator.dtos.response.GameStartRestartResponseDto;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link GameSessionToGameStartRestartResponseDto}.
 */
public class GameSessionToGameStartRestartResponseDtoTest {

    private GameSessionToGameStartRestartResponseDto converter;

    @Before
    public void setUp() {
        converter = new GameSessionToGameStartRestartResponseDto();
    }

    /**
     * Verifies that the start/restart response fields are mapped correctly.
     */
    @Test
    public void convertShouldMapStartRestartFields() {
        GameSession session = new GameSession();
        session.setSessionId("session-1");
        session.setPersonalityType(PersonalityType.DEFAULT);
        session.setRemainingLives(3);
        session.setQuestionCountInRound(0);
        session.setGameStatus(GameStatus.IN_PROGRESS);
        session.setLastAiMessage("Hello there.");

        GameStartRestartResponseDto result = converter.convert(session);

        assertEquals("session-1", result.getSessionId());
        assertEquals(PersonalityType.DEFAULT, result.getPersonalityType());
        assertEquals(3, result.getRemainingLives());
        assertEquals(0, result.getQuestionCountInRound());
        assertEquals(GameStatus.IN_PROGRESS, result.getGameStatus());
        assertEquals("Hello there.", result.getLastAiMessage());
    }
}
