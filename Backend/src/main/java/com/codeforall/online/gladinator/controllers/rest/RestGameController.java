package com.codeforall.online.gladinator.controllers.rest;

import com.codeforall.online.gladinator.dtos.request.AnswerRequestDto;
import com.codeforall.online.gladinator.dtos.request.ChoosePersonalityRequestDto;
import com.codeforall.online.gladinator.dtos.response.EndGameResponseDto;
import com.codeforall.online.gladinator.dtos.response.GameStartRestartResponseDto;
import com.codeforall.online.gladinator.dtos.response.GameStateDto;
import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;
import com.codeforall.online.gladinator.services.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for exposing the main game endpoints.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/game")
public class RestGameController {

  private GameService gameService;

  /**
   * Starts a new game session.
   *
   * @return the DTO containing the initial session state
   */
  @PostMapping("/start")
  public GameStartRestartResponseDto startGame() {
    return gameService.startGame();
  }

  /**
   * Gets the current state of a game session.
   *
   * @param sessionId the session identifier
   * @return the DTO representing the current session state
   */
  @GetMapping("/{sessionId}/state")
  public GameStateDto getGameState(@PathVariable String sessionId) {
    return gameService.getGameState(sessionId);
  }

  /**
   * Chooses or updates the AI personality for an existing session.
   *
   * @param sessionId  the session identifier
   * @param requestDto the request DTO containing the selected personality
   * @return the DTO representing the updated session state
   */
  // Alterei aqui para Patch por ser uma alteração a uma sessão já criada
  @PatchMapping("/{sessionId}/personality")
  public GameStateDto choosePersonality(@PathVariable String sessionId,
      @Valid @RequestBody ChoosePersonalityRequestDto requestDto) {
    return gameService.choosePersonality(sessionId, requestDto);
  }

  /**
   * Processes the user's answer for the current AI question or guess.
   *
   * @param sessionId  the session identifier
   * @param requestDto the request DTO containing the user's answer
   * @return the DTO representing the updated session state
   */
  @PostMapping("/{sessionId}/answer")
  public GameStateDto processAnswer(@PathVariable String sessionId,
      @Valid @RequestBody AnswerRequestDto requestDto) {
    return gameService.processAnswer(sessionId, requestDto);
  }

  /**
   * Restarts an existing game session.
   *
   * @param sessionId the session identifier
   * @return the DTO containing the reset session state
   */
  @PostMapping("/{sessionId}/restart")
  public GameStartRestartResponseDto restartGame(@PathVariable String sessionId) {
    return gameService.restartGame(sessionId);
  }

  /**
   * Ends a game session manually.
   *
   * @param sessionId the session identifier
   * @return the DTO containing the final ended state
   */
  @PostMapping("/{sessionId}/end")
  public EndGameResponseDto endGame(@PathVariable String sessionId) {
    return gameService.endGame(sessionId);
  }

  /**
   * Gets the next AI step for the given session.
   *
   * @param sessionId the session identifier
   * @return the DTO containing the next AI step
   */
  @GetMapping("/{sessionId}/next-step")
  public NextStepResponseDto getNextStep(@PathVariable String sessionId) {
    return gameService.getNextStep(sessionId);
  }

  /**
   * Sets the game service.
   *
   * @param gameService the game service to set
   */
  @Autowired
  public void setGameService(GameService gameService) {
    this.gameService = gameService;
  }
}
