package codeforall.com.online.gladinator.controllers.rest;

import codeforall.com.online.gladinator.converters.AiDecisionToNextStepResponseDto;
import codeforall.com.online.gladinator.dtos.response.NextStepResponseDto;
import codeforall.com.online.gladinator.model.ia.AiDecision;
import codeforall.com.online.gladinator.model.session.GameSession;
import codeforall.com.online.gladinator.services.AiService;
import codeforall.com.online.gladinator.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//endpoint simples para testar a OpenAi por sessão
@CrossOrigin (origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ai")
public class RestAiController {

    private AiService aiService;
    private SessionService sessionService;
    private AiDecisionToNextStepResponseDto nextStepConverter;

    //Endpoint de teste/debug: gera o próximo passo da IA para uma sessão
    @GetMapping("/{sessionId}/next-step")
    public NextStepResponseDto getNextAiStep(@PathVariable String sessionId) {
        GameSession session = sessionService.getSessionById(sessionId);
        AiDecision aiDecision = aiService.getNextStep(session);
        return nextStepConverter.convert(aiDecision);
    }

    @Autowired
    public void setNextStepConverter(AiDecisionToNextStepResponseDto nextStepConverter) {
        this.nextStepConverter = nextStepConverter;
    }

    @Autowired
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Autowired
    public void setAiService(AiService aiService) {
        this.aiService = aiService;
    }
}
