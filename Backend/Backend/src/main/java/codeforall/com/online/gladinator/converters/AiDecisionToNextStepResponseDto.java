package codeforall.com.online.gladinator.converters;

import codeforall.com.online.gladinator.dtos.response.NextStepResponseDto;
import codeforall.com.online.gladinator.model.ia.AiDecision;
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
