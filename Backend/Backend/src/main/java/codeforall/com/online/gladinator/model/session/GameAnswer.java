package codeforall.com.online.gladinator.model.session;

import codeforall.com.online.gladinator.model.enums.AnswerType;

//Resposta dada pelo utilizador
public class GameAnswer {

    private String question;
    private AnswerType answerType;
    private int questionOrder;

    public GameAnswer(String question, AnswerType answerType, int questionOrder) {
        this.question = question;
        this.answerType = answerType;
        this.questionOrder = questionOrder;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }
}
