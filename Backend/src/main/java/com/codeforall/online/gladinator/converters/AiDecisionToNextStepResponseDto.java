package com.codeforall.online.gladinator.converters;

import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;
import com.codeforall.online.gladinator.model.ia.AiDecision;
import org.springframework.stereotype.Component;

//Transforma a decisão interna da IA na resposta que o frontend recebe no endpoint:
//GET /api/game/{sessionId}/next-step
@Component
public class AiDecisionToNextStepResponseDto {

    public NextStepResponseDto convert(AiDecision aiDecision) {
        return new NextStepResponseDto(
                aiDecision.getAiDecisionType(),
                aiDecision.getContent()
        );
    }
}
