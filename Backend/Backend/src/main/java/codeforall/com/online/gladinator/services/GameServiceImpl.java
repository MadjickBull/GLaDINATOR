package codeforall.com.online.gladinator.services;

import codeforall.com.online.gladinator.converters.AiDecisionToNextStepResponseDto;
import codeforall.com.online.gladinator.converters.GameSessionToEndGameResponseDto;
import codeforall.com.online.gladinator.converters.GameSessionToGameStateDto;
import codeforall.com.online.gladinator.dtos.request.AnswerRequestDto;
import codeforall.com.online.gladinator.converters.GameSessionToGameStartRestartResponseDto;
import codeforall.com.online.gladinator.dtos.request.UpdatePersonalityRequestDto;
import codeforall.com.online.gladinator.dtos.response.EndGameResponseDto;
import codeforall.com.online.gladinator.dtos.response.GameStartRestartResponseDto;
import codeforall.com.online.gladinator.dtos.response.GameStateDto;
import codeforall.com.online.gladinator.dtos.response.NextStepResponseDto;
import codeforall.com.online.gladinator.model.enums.AnswerType;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import codeforall.com.online.gladinator.model.session.GameAnswer;
import codeforall.com.online.gladinator.model.session.GameConfig;
import codeforall.com.online.gladinator.model.session.GameSession;
import codeforall.com.online.gladinator.model.enums.AiDecisionType;
import codeforall.com.online.gladinator.model.enums.GameStatus;
import codeforall.com.online.gladinator.model.ia.AiDecision;

import java.util.ArrayList;

@Service
public class GameServiceImpl implements GameService {


    private SessionService sessionService;
    private GameConfig gameConfig;
    private AiService aiService;

    private GameSessionToGameStartRestartResponseDto startRestartConverter;
    private GameSessionToGameStateDto gameStateConverter;
    private GameSessionToEndGameResponseDto endGameConverter;
    private AiDecisionToNextStepResponseDto nextStepConverter;


    @Override
    public GameStartRestartResponseDto startGame() {

        // 1. cria a sessão em memória
        GameSession session = sessionService.createSession();

        // 2. devolve a resposta ao controller - usa o converter GameSessionToGameStartRestartResponseDto
        return startRestartConverter.convert(session);
    }

    //pode ser útil o frontend saber o estado da sessão sem mais nenhuma ação
    @Override
    public GameStateDto getGameState(String sessionId) {
        GameSession session = sessionService.getSessionById(sessionId);
        return gameStateConverter.convert(session);
    }

    @Override
    public GameStateDto updatePersonality(String sessionId, UpdatePersonalityRequestDto dto) {

        // 1. vai buscar a sessão
        GameSession session = sessionService.getSessionById(sessionId);

        // 2. atualiza só a personalidade
        session.setPersonalityType(dto.getPersonalityType());

        // 3. guarda
        sessionService.updateSession(session);

        // 4. devolve o estado atual completo
        return gameStateConverter.convert(session);
    }

    //coloquei a retornar um Dto
    @Override
    public GameStateDto processAnswer(String sessionId, AnswerRequestDto dto) {

        GameSession session = sessionService.getSessionById(sessionId);

        //Garante que só processamos respostas enquanto o jogo está ativo
        validateSessionInProgress(session);

        if (session.getQuestionCountInRound() < gameConfig.getMaxQuestionsPerRound()) {

            //acrescentei pq imagna que o frontend chama:
            //start(), updatePersonality() e processAnswer()
            //sem antes de processAnswer(), chamar getNextStep()
            //não vamos conseguir fazer session.getLastQuestion(), porque não há pergunta ainda
            //então podemos estar a responder sem haver guess
            if (session.getLastQuestion() == null) {
                throw new IllegalStateException("There is no active question to answer.");
            }


            // guarda a resposta no histórico
            GameAnswer answer = new GameAnswer(
                    session.getLastQuestion(),
                    dto.getAnswerType(),
                    session.getQuestionCountInRound() + 1
            );

            //acrescentei esta parte - porque pode ser a primeira resposta do utilizador
            if (session.getAnswersHistory() == null) {
                session.setAnswersHistory(new ArrayList<>());
            }

            session.getAnswersHistory().add(answer);

            // avança o contador
            session.setQuestionCountInRound(session.getQuestionCountInRound() + 1);

            //acrescentei porque podíamos estar a responder sem haver guess
        } else {
            if (session.getFinalGuess() == null) {
                throw new IllegalStateException("There is no active guess to answer.");
            }
            // utilizador está a responder ao GUESS da IA

            if (dto.getAnswerType() == AnswerType.YES) {
            // IA adivinhou corretamente
                    session.setGameStatus(GameStatus.AI_WON);

            } else {
                // IA errou → perde uma vida
                session.setRemainingLives(session.getRemainingLives() - 1);

                if (session.getRemainingLives() <= 0) {
                // sem mais vidas → jogador ganhou
                session.setGameStatus(GameStatus.PLAYER_WON);

                } else {
                // ainda tem vidas → começa novo round
                session.setQuestionCountInRound(0);
                //acrescentei isto:
                session.setFinalGuess(null);
                session.setLastQuestion(null);
                }
            }

        }

        sessionService.updateSession(session);

        return gameStateConverter.convert(session);

    }

    @Override
    public NextStepResponseDto getNextStep(String sessionId) {

    // 1. vai buscar a sessão
    GameSession session = sessionService.getSessionById(sessionId);

    //2. Validação de estado e personalidade
        validateSessionInProgress(session);
        validatePersonalitySelected(session);

    // 3. pede à IA o próximo passo
    AiDecision aiDecision = aiService.getNextStep(session);

    // 4. guarda o conteúdo da decisão na sessão
    if (aiDecision.getAiDecisionType() == AiDecisionType.QUESTION) {
        session.setLastQuestion(aiDecision.getContent());
        session.setLastAiMessage(aiDecision.getContent());

    } else if (aiDecision.getAiDecisionType() == AiDecisionType.GUESS) {
        session.setFinalGuess(aiDecision.getContent());
        session.setLastAiMessage(aiDecision.getContent());

    } else {
        // FINAL_MESSAGE
        session.setLastAiMessage(aiDecision.getContent());
    }

    // 5. guarda sessão atualizada
    sessionService.updateSession(session);

    // 6. devolve ao controller
    return nextStepConverter.convert(aiDecision);

    }

    @Override
    public GameStartRestartResponseDto restartGame(String sessionId) {

    GameSession session = sessionService.getSessionById(sessionId);

    //substituí o 3 pelo que já temos em initial lives - gameConfig.getInitialLives()
    session.setRemainingLives(gameConfig.getInitialLives());
    session.setQuestionCountInRound(0);
    session.setGameStatus(GameStatus.IN_PROGRESS);
    session.setAnswersHistory(new ArrayList<>());
    session.setLastQuestion(null);
    session.setLastAiMessage(null);
    session.setFinalGuess(null);

    sessionService.updateSession(session);

    return startRestartConverter.convert(session);

    }

    @Override
    public EndGameResponseDto endGame(String sessionId) {

    // 1. vai buscar a sessão
    GameSession session = sessionService.getSessionById(sessionId);

    // 2. marca como terminada manualmente
    session.setGameStatus(GameStatus.ENDED);

    // 3. guarda
    sessionService.updateSession(session);

    // 4. devolve confirmação
    EndGameResponseDto endGameResponseDto = endGameConverter.convert(session);
    sessionService.deleteSessionById(sessionId);

    return endGameResponseDto;
    }

    private void validateSessionInProgress(GameSession session) {
        if (session.getGameStatus() != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Game session is not in progress.");
        }
    }

    private void validatePersonalitySelected(GameSession session) {
        if (session.getPersonalityType() == null) {
            throw new IllegalStateException("Personality must be selected before requesting the next step.");
        }
    }

    @Autowired
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Autowired
    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    @Autowired
    public void setStartRestartConverter(GameSessionToGameStartRestartResponseDto startRestartConverter) {
        this.startRestartConverter = startRestartConverter;
    }

    @Autowired
    public void setGameStateConverter(GameSessionToGameStateDto gameStateConverter) {
        this.gameStateConverter = gameStateConverter;
    }

    @Autowired
    public void setEndGameConverter(GameSessionToEndGameResponseDto endGameConverter) {
        this.endGameConverter = endGameConverter;
    }

    @Autowired
    public void setNextStepConverter(AiDecisionToNextStepResponseDto nextStepConverter) {
        this.nextStepConverter= nextStepConverter;
    }

    @Autowired
    public void setAiService(AiService aiService) {
        this.aiService = aiService;
    }
}