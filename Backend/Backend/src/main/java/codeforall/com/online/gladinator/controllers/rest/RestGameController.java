package codeforall.com.online.gladinator.controllers.rest;

import codeforall.com.online.gladinator.dtos.request.AnswerRequestDto;
import codeforall.com.online.gladinator.dtos.request.UpdatePersonalityRequestDto;
import codeforall.com.online.gladinator.dtos.response.EndGameResponseDto;
import codeforall.com.online.gladinator.dtos.response.GameStartRestartResponseDto;
import codeforall.com.online.gladinator.dtos.response.GameStateDto;
import codeforall.com.online.gladinator.dtos.response.NextStepResponseDto;
import codeforall.com.online.gladinator.services.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestGameController {

    private final GameService gameService;

    public RestGameController(GameService gameService) {
        this.gameService = gameService;
    }

    private final GameService gameService;

    public RestGameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public GameStartRestartResponseDto startGame() {
        return gameService.startGame();
    }

    @GetMapping("/{sessionId}/state")
    public GameStateDto getGameState(@PathVariable String sessionId) {
        return gameService.getGameState(sessionId);
    }

    @PatchMapping("/{sessionId}/personality")
    public GameStateDto updatePersonality(@PathVariable String sessionId,
                                          @RequestBody UpdatePersonalityRequestDto requestDto) {
        return gameService.updatePersonality(sessionId, requestDto);
    }

    @PostMapping("/{sessionId}/answer")
    public GameStateDto submitAnswer(@PathVariable String sessionId,
                                     @RequestBody AnswerRequestDto requestDto) {
        return gameService.submitAnswer(sessionId, requestDto);
    }

    @PostMapping("/{sessionId}/restart")
    public GameStartRestartResponseDto restartGame(@PathVariable String sessionId) {
        return gameService.restartGame(sessionId);
    }

    @PostMapping("/{sessionId}/end")
    public EndGameResponseDto endGame(@PathVariable String sessionId) {
        return gameService.endGame(sessionId);
    }

    @GetMapping("/{sessionId}/next-step")
    public NextStepResponseDto getNextStep(@PathVariable String sessionId) {
        return gameService.getNextStep(sessionId);
    }
}
