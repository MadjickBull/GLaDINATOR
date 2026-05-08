package com.codeforall.online.gladinator.model.session;

import org.springframework.stereotype.Component;

//Guarda as regras fixas do jogo
@Component
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
