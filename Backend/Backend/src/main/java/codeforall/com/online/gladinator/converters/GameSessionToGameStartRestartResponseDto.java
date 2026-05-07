package codeforall.com.online.gladinator.converters;

import codeforall.com.online.gladinator.dtos.response.GameStartRestartResponseDto;
import codeforall.com.online.gladinator.model.session.GameSession;
import org.springframework.stereotype.Component;

//Transforma a sessão num DTO mais resumido, usado para as respostas de:
//POST /api/game/start
//POST /api/game/{sessionId}/restart
@Component
public class GameSessionToGameStartRestartResponseDto {
    public GameStartRestartResponseDto convert (GameSession gameSession) {
        return new GameStartRestartResponseDto(
                gameSession.getSessionId(),
                gameSession.getPersonalityType(),
                gameSession.getRemainingLives(),
                gameSession.getQuestionCountInRound(),
                gameSession.getGameStatus()
        );
    }

}
