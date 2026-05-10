package com.codeforall.online.gladinator.model.ia;

import com.codeforall.online.gladinator.model.enums.AiDecisionType;

/**
 * Represents the structured decision returned by the AI for the next game step.
 */
public class AiDecision {

    private final AiDecisionType aiDecisionType;
    private final String content;

    /**
     * Creates a new AI decision.
     *
     * @param aiDecisionType the type of decision returned by the AI
     * @param content the textual content associated with the decision
     */
    public AiDecision(AiDecisionType aiDecisionType, String content) {
        this.aiDecisionType = aiDecisionType;
        this.content = content;
    }

    /**
     * Gets the type of AI decision.
     *
     * @return the AI decision type
     */
    public AiDecisionType getAiDecisionType() {
        return aiDecisionType;
    }

    /**
     * Gets the decision content.
     *
     * @return the decision text
     */
    public String getContent() {
        return content;
    }
}
