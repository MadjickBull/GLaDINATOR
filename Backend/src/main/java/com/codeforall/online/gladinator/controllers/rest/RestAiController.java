package com.codeforall.online.gladinator.controllers.rest;

import com.codeforall.online.gladinator.converters.AiDecisionToNextStepResponseDto;
import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;
import com.codeforall.online.gladinator.model.ia.AiDecision;
import com.codeforall.online.gladinator.model.session.GameSession;
import com.codeforall.online.gladinator.services.AiService;
import com.codeforall.online.gladinator.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller used only for AI debug endpoints.
 * The official game flow should always go through GameService and RestGameController.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ai")
public class RestAiController {

    private AiService aiService;
    private SessionService sessionService;
    private AiDecisionToNextStepResponseDto nextStepConverter;

    /**
     * Generates the next AI step directly for a given session.
     * This endpoint is intended only for debug or isolated AI testing.
     *
     * @param sessionId the session identifier
     * @return the DTO containing the next AI step
     */
    @GetMapping("/{sessionId}/next-step")
    public NextStepResponseDto getNextAiStep(@PathVariable String sessionId) {
        GameSession session = sessionService.getSessionById(sessionId);
        AiDecision aiDecision = aiService.getNextStep(session);
        return nextStepConverter.convert(aiDecision);
    }

    /**
     * Sets the next-step converter.
     *
     * @param nextStepConverter the converter to set
     */
    @Autowired
    public void setNextStepConverter(AiDecisionToNextStepResponseDto nextStepConverter) {
        this.nextStepConverter = nextStepConverter;
    }

    /**
     * Sets the session service.
     *
     * @param sessionService the session service to set
     */
    @Autowired
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * Sets the AI service.
     *
     * @param aiService the AI service to set
     */
    @Autowired
    public void setAiService(AiService aiService) {
        this.aiService = aiService;
    }
}
