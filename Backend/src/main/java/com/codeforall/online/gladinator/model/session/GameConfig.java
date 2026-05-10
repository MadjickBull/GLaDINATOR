package com.codeforall.online.gladinator.model.session;

/**
 * Represents the fixed game rules used across the application.
 */
public class GameConfig {

    private final int initialLives;
    private final int maxQuestionsPerRound;

    /**
     * Creates a new game configuration.
     *
     * @param initialLives the number of initial lives assigned to the AI
     * @param maxQuestionsPerRound the maximum number of questions allowed per round
     */
    public GameConfig(int initialLives, int maxQuestionsPerRound) {
        this.initialLives = initialLives;
        this.maxQuestionsPerRound = maxQuestionsPerRound;
    }

    /**
     * Gets the initial number of AI lives.
     *
     * @return the initial lives value
     */
    public int getInitialLives() {
        return initialLives;
    }

    /**
     * Gets the maximum number of questions allowed per round.
     *
     * @return the maximum number of questions per round
     */
    public int getMaxQuestionsPerRound() {
        return maxQuestionsPerRound;
    }
}
