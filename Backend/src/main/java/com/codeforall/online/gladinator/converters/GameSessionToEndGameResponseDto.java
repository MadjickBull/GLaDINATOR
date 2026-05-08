package com.codeforall.online.gladinator.converters;

import com.codeforall.online.gladinator.dtos.response.EndGameResponseDto;
import com.codeforall.online.gladinator.model.session.GameSession;
import org.springframework.stereotype.Component;

@Component
public class GameSessionToEndGameResponseDto {

    public EndGameResponseDto convert(GameSession gameSession) {
        return new EndGameResponseDto(
                gameSession.getSessionId(),
                gameSession.getGameStatus()
        );
    }
}
