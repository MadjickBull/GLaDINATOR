package com.codeforall.online.gladinator.dtos.response;

import com.codeforall.online.gladinator.model.enums.AiDecisionType;

/**
 * Response DTO that represents the next decision returned by the AI.
 */
public class NextStepResponseDto {

    private AiDecisionType type;
    private String content;

    public NextStepResponseDto() {
    }

    public NextStepResponseDto(AiDecisionType type, String content) {
        this.type = type;
        this.content = content;
    }

    public AiDecisionType getType() {
        return type;
    }

    public void setType(AiDecisionType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
