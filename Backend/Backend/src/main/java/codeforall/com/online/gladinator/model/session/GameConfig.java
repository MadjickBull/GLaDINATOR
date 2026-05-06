package codeforall.com.online.gladinator.model.session;

//Guarda as regras fixas do jogo (pode depois passar a Constant...)

public class GameConfig {

    private int initialLives;
    private int maxQuestionsPerRound;

    public GameConfig() {
        this.initialLives = 3;
        this.maxQuestionsPerRound = 4;
    }

    public GameConfig(int initialLives, int maxQuestionsPerRound) {
        this.initialLives = initialLives;
        this.maxQuestionsPerRound = maxQuestionsPerRound;
    }

    public int getInitialLives() {
        return initialLives;
    }

    public void setInitialLives(int initialLives) {
        this.initialLives = initialLives;
    }

    public int getMaxQuestionsPerRound() {
        return maxQuestionsPerRound;
    }
     public void setMaxQuestionsPerRound(int maxQuestionsPerRound) {
        this.maxQuestionsPerRound = maxQuestionsPerRound;
     }

}
