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
    private ChatClient chatClient;

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

        String prompt = buildPrompt(gameSession);
        String content = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        if (gameSession.getQuestionCountInRound() < gameConfig.getMaxQuestionsPerRound() {
            return new AiDecision(
                    AiDecisionType.QUESTION;
                    content
            );
        }

        return new AiDecision(
                AiDecisionType.GUESS;
                content
        );
    }

    //metodo que dá contexto à IA - constrói o prompt completo que vai ser enviado à OpenAI
    private String buildPrompt(GameSession gameSession) {}

        /*recebe a GameSession
          vai buscar o estado atual do jogo
          vai buscar a personalidade atual
          vai buscar o número de vidas restantese String buildHistory(GameSession gameSession)
          vai buscar o número de perguntas já feitas na rondaturn null;
          vai buscar o limite máximo de perguntas por ronda
          chama o buildHistory(...) para incluir o histórico anterior
          junta tudo num texto estruturado
          acrescenta instruções à IA sobre como deve responder
         */

         private String buildHistory(GameSession gameSession) {}
        //metodo que constrói só a parte do histórico textual da sessão
        //para dar à OpenAI memória do que já aconteceu na ronda/jogo

            /*recebe a GameSession
            verifica se existe answersHistory
            (se não existir histórico, devolve uma mensagem simples tipo:
            "No previous questions or answers."

            se existir histórico:
            percorre os GameAnswer
            transforma cada resposta num texto legível
            junta tudo numa string

            Exemplo: Question 1: Is your character human? | Answer: YES
            Exemplo: Question 2: Is your character from a movie? | Answer: NO

    */
    @Autowired
    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    @Autowired
    public void setChatClient (ChatClient chatClient) {
        this chatClient = chatClient;
    }
}

