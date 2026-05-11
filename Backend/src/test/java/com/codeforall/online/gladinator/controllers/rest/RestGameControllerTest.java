package com.codeforall.online.gladinator.controllers.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codeforall.online.gladinator.dtos.response.GameStartRestartResponseDto;
import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;
import com.codeforall.online.gladinator.model.enums.AiDecisionType;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
import com.codeforall.online.gladinator.services.GameService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Lightweight controller tests for the main game endpoints.
 */
public class RestGameControllerTest {

  private MockMvc mockMvc;
  private GameService gameService;

  @Before
  public void setUp() {
    RestGameController controller = new RestGameController();
    gameService = mock(GameService.class);
    controller.setGameService(gameService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setMessageConverters(new MappingJackson2HttpMessageConverter())
        .build();
  }

  /**
   * Verifies that the start endpoint returns the default personality in the response.
   */
  @Test
  public void startGameShouldReturnDefaultPersonality() throws Exception {
    when(gameService.startGame()).thenReturn(
        new GameStartRestartResponseDto("session-1", PersonalityType.DEFAULT, 3, 0,
            GameStatus.IN_PROGRESS, null));

    mockMvc.perform(post("/api/game/start"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sessionId").value("session-1"))
        .andExpect(jsonPath("$.personalityType").value("DEFAULT"))
        .andExpect(jsonPath("$.remainingLives").value(3))
        .andExpect(jsonPath("$.gameStatus").value("IN_PROGRESS"));
  }

  /**
   * Verifies that the next-step endpoint returns the AI decision payload.
   */
  @Test
  public void getNextStepShouldReturnAiDecisionPayload() throws Exception {
    when(gameService.getNextStep("session-2")).thenReturn(
        new NextStepResponseDto(AiDecisionType.QUESTION, "Is your character human?"));

    mockMvc.perform(get("/api/game/session-2/next-step"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.type").value("QUESTION"))
        .andExpect(jsonPath("$.content").value("Is your character human?"));
  }
}
