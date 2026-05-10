package com.codeforall.online.gladinator.converters;

import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;
import com.codeforall.online.gladinator.model.ia.AiDecision;
import org.springframework.stereotype.Component;

/**
 * Converts an internal AI decision into the DTO returned by the next-step endpoint.
 */
@Component
public class AiDecisionToNextStepResponseDto {

    /**
     * Converts an {@link AiDecision} into a {@link NextStepResponseDto}.
     *
     * @param aiDecision the internal AI decision
     * @return the DTO returned to the frontend
     */
    public NextStepResponseDto convert(AiDecision aiDecision) {
        return new NextStepResponseDto(
                aiDecision.getAiDecisionType(),
                aiDecision.getContent()
        );
    }
}
