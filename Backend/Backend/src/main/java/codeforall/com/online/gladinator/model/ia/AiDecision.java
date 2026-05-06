package codeforall.com.online.gladinator.model.ia;

import codeforall.com.online.gladinator.model.enums.AiDecisionType;

public class AiDecision {

    private AiDecisionType aiDecisionType;
    private String content;


    public AiDecision(AiDecisionType aiDecisionType, String content) {
        this.aiDecisionType = aiDecisionType;
        this.content = content;
    }

    public AiDecisionType getAiDecisionType() {
        return aiDecisionType;
    }

    public void setAiDecisionType(AiDecisionType aiDecisionType) {
        this.aiDecisionType = aiDecisionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
