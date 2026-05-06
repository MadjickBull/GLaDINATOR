package codeforall.com.online.gladinator.model.session;

import codeforall.com.online.gladinator.model.enums.GameStatus;
import codeforall.com.online.gladinator.model.enums.PersonalityType;
import java.util.List;

public class GameSession {

    //String -> p/podermos usar um identificador único em vez de termos um contador manual - UUID
    private String sessionId;
    //pode mudar a qualquer momento
    private PersonalityType personalityType;
    private int remainingLives;
    //até 4 perguntas -> depois guess
    private int questionCountInRound;
    private GameStatus gameStatus;
    private String lastQuestion;
    private String lastAiMessage;
    private String finalGuess;

    //importante para
        // o backend manter histórico
        //a OpenAI receber contexto
    private List<GameAnswer> answersHistory;


    public GameSession() {}

    public GameSession(String sessionId, PersonalityType personalityType, int remainingLives, int questionCountInRound, GameStatus gameStatus, String lastQuestion, String lastAiMessage, String finalGuess, List<GameAnswer> answersHistory) {
        this.sessionId = sessionId;
        this.personalityType = personalityType;
        this.remainingLives = remainingLives;
        this.questionCountInRound = questionCountInRound;
        this.gameStatus = gameStatus;
        this.lastQuestion = lastQuestion;
        this.lastAiMessage = lastAiMessage;
        this.finalGuess = finalGuess;
        this.answersHistory = answersHistory;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public PersonalityType getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(PersonalityType personalityType) {
        this.personalityType = personalityType;
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    public int getQuestionCountInRound() {
        return questionCountInRound;
    }

    public void setQuestionCountInRound(int questionCountInRound) {
        this.questionCountInRound = questionCountInRound;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getLastQuestion() {
        return lastQuestion;
    }

    public void setLastQuestion(String lastQuestion) {
        this.lastQuestion = lastQuestion;
    }

    public String getLastAiMessage() {
        return lastAiMessage;
    }

    public void setLastAiMessage(String lastAiMessage) {
        this.lastAiMessage = lastAiMessage;
    }

    public String getFinalGuess() {
        return finalGuess;
    }

    public void setFinalGuess(String finalGuess) {
        this.finalGuess = finalGuess;
    }

    public List<GameAnswer> getAnswersHistory() {
        return answersHistory;
    }

    public void setAnswersHistory(List<GameAnswer> answersHistory) {
        this.answersHistory = answersHistory;
    }
}
