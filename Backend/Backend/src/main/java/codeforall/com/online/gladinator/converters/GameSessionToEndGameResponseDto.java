package codeforall.com.online.gladinator.converters;

import codeforall.com.online.gladinator.dtos.response.EndGameResponseDto;
import codeforall.com.online.gladinator.model.session.GameSession;
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
