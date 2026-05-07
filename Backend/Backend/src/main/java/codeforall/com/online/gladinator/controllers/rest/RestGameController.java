package codeforall.com.online.gladinator.controllers.rest;

import codeforall.com.online.gladinator.dtos.request.AnswerRequestDto;
import codeforall.com.online.gladinator.dtos.request.UpdatePersonalityRequestDto;
import codeforall.com.online.gladinator.dtos.response.EndGameResponseDto;
import codeforall.com.online.gladinator.dtos.response.GameStartRestartResponseDto;
import codeforall.com.online.gladinator.dtos.response.GameStateDto;
import codeforall.com.online.gladinator.dtos.response.NextStepResponseDto;
import codeforall.com.online.gladinator.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class RestGameController {

    private GameService gameService;

    // Cria uma nova sessão de jogo
    @PostMapping("/start")
    public GameStartRestartResponseDto startGame() {
        return gameService.startGame();
    }

    // Devolve o estado atual da sessão
    @GetMapping("/{sessionId}/state")
    public GameStateDto getGameState(@PathVariable String sessionId) {
        return gameService.getGameState(sessionId);
    }

    // Define ou altera a personalidade da IA
    @PatchMapping("/{sessionId}/personality")
    public GameStateDto updatePersonality(@PathVariable String sessionId,
                                          @RequestBody UpdatePersonalityRequestDto requestDto) {
        return gameService.updatePersonality(sessionId, requestDto);
    }

    // Processa a resposta do utilizador
    @PostMapping("/{sessionId}/answer")
    public GameStateDto processAnswer(@PathVariable String sessionId,
                                      @RequestBody AnswerRequestDto requestDto) {
        return gameService.processAnswer(sessionId, requestDto);
    }

    // Reinicia a sessão mantendo o mesmo sessionId
    @PostMapping("/{sessionId}/restart")
    public GameStartRestartResponseDto restartGame(@PathVariable String sessionId) {
        return gameService.restartGame(sessionId);
    }

    // Termina manualmente a sessão
    @PostMapping("/{sessionId}/end")
    public EndGameResponseDto endGame(@PathVariable String sessionId) {
        return gameService.endGame(sessionId);
    }

    //Pede o próximo passo da IA: pergunta, guess ou mensagem final
    @GetMapping("/{sessionId}/next-step")
    public NextStepResponseDto getNextStep(@PathVariable String sessionId) {
        return gameService.getNextStep(sessionId);
    }

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}
