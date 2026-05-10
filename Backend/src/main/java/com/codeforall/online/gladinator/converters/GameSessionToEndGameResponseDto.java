package com.codeforall.online.gladinator.converters;

import com.codeforall.online.gladinator.dtos.response.EndGameResponseDto;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.springframework.stereotype.Component;

/**
 * Converts a game session into the DTO returned when a session is ended.
 */
@Component
public class GameSessionToEndGameResponseDto {

    /**
     * Converts a {@link GameSession} into an {@link EndGameResponseDto}.
     *
     * @param gameSession the current game session
     * @return the DTO returned to the frontend
     */
    public EndGameResponseDto convert(GameSession gameSession) {
        return new EndGameResponseDto(
                gameSession.getSessionId(),
                gameSession.getGameStatus()
        );
    }
}
