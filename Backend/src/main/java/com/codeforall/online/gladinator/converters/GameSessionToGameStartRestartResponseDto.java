package com.codeforall.online.gladinator.converters;

import com.codeforall.online.gladinator.dtos.response.GameStartRestartResponseDto;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.springframework.stereotype.Component;

/**
 * Converts a game session into the DTO returned by the start and restart endpoints.
 */
@Component
public class GameSessionToGameStartRestartResponseDto {

    /**
     * Converts a {@link GameSession} into a {@link GameStartRestartResponseDto}.
     *
     * @param gameSession the current game session
     * @return the DTO returned to the frontend
     */
    public GameStartRestartResponseDto convert(GameSession gameSession) {
        return new GameStartRestartResponseDto(
                gameSession.getSessionId(),
                gameSession.getPersonalityType(),
                gameSession.getRemainingLives(),
                gameSession.getQuestionCountInRound(),
                gameSession.getGameStatus(),
                gameSession.getLastAiMessage()
        );
    }
}
