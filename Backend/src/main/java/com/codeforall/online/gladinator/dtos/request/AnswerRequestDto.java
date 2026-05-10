package com.codeforall.online.gladinator.dtos.request;

import com.codeforall.online.gladinator.model.enums.AnswerType;
import jakarta.validation.constraints.NotNull;

/**
 * Request DTO used to send the user's answer to the backend.
 */
public class AnswerRequestDto {

    @NotNull
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
