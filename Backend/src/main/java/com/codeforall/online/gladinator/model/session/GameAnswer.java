package com.codeforall.online.gladinator.model.session;

import com.codeforall.online.gladinator.model.enums.AnswerType;

/**
 * Represents an answer given by the user to an AI question.
 */
public class GameAnswer {

    private final String question;
    private final AnswerType answerType;
    private final int questionOrder;

    /**
     * Creates a new game answer record.
     *
     * @param question the AI question associated with the answer
     * @param answerType the answer selected by the user
     * @param questionOrder the order of the question in the current session history
     */
    public GameAnswer(String question, AnswerType answerType, int questionOrder) {
        this.question = question;
        this.answerType = answerType;
        this.questionOrder = questionOrder;
    }

    /**
     * Gets the related AI question.
     *
     * @return the related question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Gets the answer selected by the user.
     *
     * @return the answer type
     */
    public AnswerType getAnswerType() {
        return answerType;
    }

    /**
     * Gets the position of this question in the session history.
     *
     * @return the question order
     */
    public int getQuestionOrder() {
        return questionOrder;
    }
}
