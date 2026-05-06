package codeforall.com.online.gladinator.model.ia;

public class AiDecision {

    private codeforall.com.online.gladinator.model.ia.AiDecision aiDecisionType;
    private String content;


    public AiDecision(codeforall.com.online.gladinator.model.ia.AiDecision aiDecisionType, String content) {
        this.aiDecisionType = aiDecisionType;
        this.content = content;
    }

    public codeforall.com.online.gladinator.model.ia.AiDecision getAiDecisionType() {
        return aiDecisionType;
    }

    public void setAiDecisionType(codeforall.com.online.gladinator.model.ia.AiDecision aiDecisionType) {
        this.aiDecisionType = aiDecisionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
