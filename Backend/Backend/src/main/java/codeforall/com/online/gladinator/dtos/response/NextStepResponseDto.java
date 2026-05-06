package codeforall.com.online.gladinator.dtos.response;

import codeforall.com.online.gladinator.model.enums.AiDecisionType;

//Resposta do endpoint next-step - para pergunta ou guess
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
