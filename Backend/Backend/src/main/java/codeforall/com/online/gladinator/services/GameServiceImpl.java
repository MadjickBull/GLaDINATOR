package codeforall.com.online.gladinator.services;

import codeforall.com.online.gladinator.dtos.request.AnswerRequestDto;
import codeforall.com.online.gladinator.dtos.request.StartGameRequestDto;
import codeforall.com.online.gladinator.dtos.request.UpdatePersonalityRequestDto;
import codeforall.com.online.gladinator.dtos.response.EndGameResponseDto;
import codeforall.com.online.gladinator.dtos.response.GameStartRestartResponseDto;
import codeforall.com.online.gladinator.dtos.response.GameStateDto;
import codeforall.com.online.gladinator.dtos.response.NextStepResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import codeforall.com.online.gladinator.model.session.GameAnswer;
import codeforall.com.online.gladinator.model.session.GameConfig;
import codeforall.com.online.gladinator.model.session.GameSession;
import codeforall.com.online.gladinator.model.enums.AiDecisionType;
import codeforall.com.online.gladinator.model.enums.AnswerType;
import codeforall.com.online.gladinator.model.enums.GameStatus;
import codeforall.com.online.gladinator.model.ia.AiDecision;

import java.util.ArrayList;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private SessionService sessionService;
    
    @Autowired
    private GameConfig gameConfig;

    @Autowired
    private AiService aiService;

    @Override
    public GameStartRestartResponseDto startGame(StartGameRequestDto dto) {

        // 1. cria a sessão em memória
        GameSession session = sessionService.createSession();

        // 2. define o estado inicial do jogo
        session.setPersonalityType(dto.getPersonalityType());
        session.setRemainingLives(3);
        session.setQuestionCountInRound(0);
        session.setGameStatus(GameStatus.IN_PROGRESS);
        session.setAnswersHistory(new ArrayList<>());

        // 3. guarda a sessão atualizada
        sessionService.updateSession(session);

        // 4. devolve a resposta ao controller
        return new GameStartRestartResponseDto(
                session.getSessionId(),
                session.getPersonalityType(),
                session.getRemainingLives(),
                session.getQuestionCountInRound(),
                session.getGameStatus()
        );
    }

    @Override
    public NextStepResponseDto getNextStep(String sessionId) {

    // 1. vai buscar a sessão
    GameSession session = sessionService.getSessionById(sessionId);

    // 2. pede à IA o próximo passo
    AiDecision decision = aiService.getNextStep(session);

    // 3. guarda o conteúdo da decisão na sessão
    if (decision.getAiDecisionType() == AiDecisionType.QUESTION) {
        session.setLastQuestion(decision.getContent());
        session.setLastAiMessage(decision.getContent());

    } else if (decision.getAiDecisionType() == AiDecisionType.GUESS) {
        session.setFinalGuess(decision.getContent());
        session.setLastAiMessage(decision.getContent());

    } else {
        // FINAL_MESSAGE
        session.setLastAiMessage(decision.getContent());
    }

    // 4. guarda sessão atualizada
    sessionService.updateSession(session);

    // 5. devolve ao controller
    return new NextStepResponseDto(
            decision.getAiDecisionType(),
            decision.getContent()
    );
    }

    @Override
    public void processAnswer(String sessionId, AnswerRequestDto dto) {

    GameSession session = sessionService.getSessionById(sessionId);

    // utilizador está a responder a uma PERGUNTA
    if (session.getQuestionCountInRound() < gameConfig.getMaxQuestionsPerRound()) {

        // guarda a resposta no histórico
        GameAnswer answer = new GameAnswer(
                session.getLastQuestion(),
                dto.getAnswerType(),
                session.getQuestionCountInRound() + 1
        );
        session.getAnswersHistory().add(answer);

        // avança o contador
        session.setQuestionCountInRound(session.getQuestionCountInRound() + 1);

    } else {
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
            }
        }
    }

    sessionService.updateSession(session);
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
    return new GameStateDto(
            session.getSessionId(),
            session.getPersonalityType(),
            session.getRemainingLives(),
            session.getQuestionCountInRound(),
            session.getGameStatus(),
            session.getLastQuestion(),
            session.getLastAiMessage(),
            session.getFinalGuess()
    );
    }

    @Override
    public GameStartRestartResponseDto restart(String sessionId) {

    GameSession session = sessionService.getSessionById(sessionId);

    session.setRemainingLives(3);
    session.setQuestionCountInRound(0);
    session.setGameStatus(GameStatus.IN_PROGRESS);
    session.setAnswersHistory(new ArrayList<>());
    session.setLastQuestion(null);
    session.setLastAiMessage(null);
    session.setFinalGuess(null);

    sessionService.updateSession(session);

    return new GameStartRestartResponseDto(
            session.getSessionId(),
            session.getPersonalityType(),
            session.getRemainingLives(),    
            session.getQuestionCountInRound(),
            session.getGameStatus()
    );
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
    return new EndGameResponseDto(
            session.getSessionId(),
            session.getGameStatus()
    );
    }
}