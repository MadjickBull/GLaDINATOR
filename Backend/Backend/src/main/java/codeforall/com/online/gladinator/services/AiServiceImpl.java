package codeforall.com.online.gladinator.services;

import codeforall.com.online.gladinator.model.enums.AiDecisionType;
import codeforall.com.online.gladinator.model.enums.GameStatus;
import codeforall.com.online.gladinator.model.ia.AiDecision;
import codeforall.com.online.gladinator.model.session.GameConfig;
import codeforall.com.online.gladinator.model.session.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//gerar a próxima perguta, gerar uma guess, gerar uma mensagem final
public class AiServiceImpl implements AiService {

    private GameConfig gameConfig;

    //content de AiDecision com estrutura temporária - até OpenAi ligada
    @Override
    public AiDecision getNextStep(GameSession gameSession) {

        //verificamos se já terminou
        if (gameSession.getGameStatus() == GameStatus.PLAYER_WON) {
            return new AiDecision(
                    AiDecisionType.FINAL_MESSAGE,
                    "You win. I was clearly sabotaged."
            );
        }

        if (gameSession.getGameStatus() == GameStatus.AI_WON) {
            return new AiDecision(
                    AiDecisionType.FINAL_MESSAGE,
                    "I knew it. Your character was obvious."
            );
        }

        if (gameSession.getGameStatus() == GameStatus.IN_PROGRESS) {

            if (gameSession.getQuestionCountInRound() < gameConfig.getMaxQuestionsPerRound()) {
                return new AiDecision(
                        AiDecisionType.QUESTION,
                        "Is your character human?"
                );
            }

            return new AiDecision(
                    AiDecisionType.GUESS,
                    "You are thinking of HAL 9000."
            );
        }

        //Estado inesperado
        return new AiDecision(
                AiDecisionType.FINAL_MESSAGE,
                "Session ended."
        );
    }

    @Autowired
    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }
}

