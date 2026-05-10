package com.codeforall.online.gladinator.converters;

import com.codeforall.online.gladinator.dtos.response.GameStateDto;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link GameSessionToGameStateDto}.
 */
public class GameSessionToGameStateDtoTest {

    private GameSessionToGameStateDto converter;

    @Before
    public void setUp() {
        converter = new GameSessionToGameStateDto();
    }

    /**
     * Verifies that the current game state fields are mapped correctly.
     */
    @Test
    public void convertShouldMapCurrentGameStateFields() {
        GameSession session = new GameSession();
        session.setSessionId("session-1");
        session.setPersonalityType(PersonalityType.SARCASTIC);
        session.setRemainingLives(2);
        session.setQuestionCountInRound(3);
        session.setGameStatus(GameStatus.IN_PROGRESS);
        session.setLastQuestion("Is your character fictional?");
        session.setLastAiMessage("Is your character fictional?");
        session.setFinalGuess("Batman");

        GameStateDto result = converter.convert(session);

        assertEquals("session-1", result.getSessionId());
        assertEquals(PersonalityType.SARCASTIC, result.getPersonalityType());
        assertEquals(2, result.getRemainingLives());
        assertEquals(3, result.getQuestionCountInRound());
        assertEquals(GameStatus.IN_PROGRESS, result.getGameStatus());
        assertEquals("Is your character fictional?", result.getLastQuestion());
        assertEquals("Is your character fictional?", result.getLastAiMessage());
        assertEquals("Batman", result.getFinalGuess());
    }
}
