package codeforall.com.online.gladinator.dtos.request;

import codeforall.com.online.gladinator.model.enums.AnswerType;

//Serve para enviar a resposta do utilizador.
public class AnswerRequestDto {

    private AnswerType answerType;

    public AnswerRequestDto() {
    }

    public AnswerRequestDto(AnswerType answerType) {
        this.answerType = answerType;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }
}
