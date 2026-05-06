package codeforall.com.online.gladinator.dtos.response;

import codeforall.com.online.gladinator.model.enums.GameStatus;
import codeforall.com.online.gladinator.model.enums.PersonalityType;

//estado atual da sessão
public class GameStateDto {

    private String sessionId;
    private PersonalityType personalityType;
    private int remainingLives;
    private int questionCountInRound;
    private GameStatus gameStatus;

    //para o frontend saber qual foi a última pergunta feita pela IA
    private String lastQuestion;

    // para guardar a última mensagem textual da IA, que pode não ser exatamente uma pergunta
        //A A IA pode perguntar: Is your character human? mas antes disso dizer ex: Let's make this easy for you.
    private String lastAiMessage;

    //guardar a guess mais recente ou final da IA
    //quando a IA tenta adivinhar, o frontend precisa de saber:
    //qual foi a guess
    //o que mostrar no resultado
    //o que tocar em TTS
    //o que reapresentar se houver refresh
    private String finalGuess;

    public GameStateDto() {}

    public GameStateDto(String sessionId, PersonalityType personalityType, int remainingLives, int questionCountInRound, GameStatus gameStatus, String lastQuestion, String lastAiMessage, String finalGuess) {
        this.sessionId = sessionId;
        this.personalityType = personalityType;
        this.remainingLives = remainingLives;
        this.questionCountInRound = questionCountInRound;
        this.gameStatus = gameStatus;
        this.lastQuestion = lastQuestion;
        this.lastAiMessage = lastAiMessage;
        this.finalGuess = finalGuess;
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
}
