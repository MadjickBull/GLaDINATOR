package codeforall.com.online.gladinator.converters;

import codeforall.com.online.gladinator.dtos.response.GameStateDto;
import codeforall.com.online.gladinator.model.session.GameSession;
import org.springframework.stereotype.Component;

//Pega no estado interno completo da sessão e transforma-o no DTO usado no endpoint:
//GET /api/game/{sessionId}/state
@Component
public class GameSessionToGameStateDto {

    public GameStateDto convert (GameSession gameSession) {
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
