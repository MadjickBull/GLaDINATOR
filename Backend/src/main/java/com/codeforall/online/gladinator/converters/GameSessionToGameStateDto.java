package com.codeforall.online.gladinator.converters;

import com.codeforall.online.gladinator.dtos.response.GameStateDto;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.springframework.stereotype.Component;

/**
 * Converts a game session into the DTO that represents the current game state.
 */
@Component
public class GameSessionToGameStateDto {

    /**
     * Converts a {@link GameSession} into a {@link GameStateDto}.
     *
     * @param gameSession the current game session
     * @return the DTO returned to the frontend
     */
    public GameStateDto convert(GameSession gameSession) {
        return new GameStateDto(
                gameSession.getSessionId(),
                gameSession.getPersonalityType(),
                gameSession.getRemainingLives(),
                gameSession.getQuestionCountInRound(),
                gameSession.getGameStatus(),
                gameSession.getLastQuestion(),
                gameSession.getLastAiMessage(),
                gameSession.getFinalGuess()
        );
    }
}
