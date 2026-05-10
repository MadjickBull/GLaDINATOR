package com.codeforall.online.gladinator.controllers.rest;

import com.codeforall.online.gladinator.dtos.request.AnswerRequestDto;
import com.codeforall.online.gladinator.dtos.request.ChoosePersonalityRequestDto;
import com.codeforall.online.gladinator.dtos.response.EndGameResponseDto;
import com.codeforall.online.gladinator.dtos.response.GameStartRestartResponseDto;
import com.codeforall.online.gladinator.dtos.response.GameStateDto;
import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;
import com.codeforall.online.gladinator.model.enums.AiDecisionType;
import com.codeforall.online.gladinator.model.enums.AnswerType;
import com.codeforall.online.gladinator.model.enums.GameStatus;
import com.codeforall.online.gladinator.model.enums.PersonalityType;
import com.codeforall.online.gladinator.services.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Controller tests for {@link RestGameController} using standalone MockMvc setup.
 */
public class RestGameControllerTest {

    private MockMvc mockMvc;
    private GameService gameService;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        RestGameController controller = new RestGameController();
        gameService = mock(GameService.class);
        controller.setGameService(gameService);

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(validator)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    /**
     * Verifies that the start endpoint returns the session payload produced by the service.
     */
    @Test
    public void startGameShouldReturnCreatedSession() throws Exception {
        GameStartRestartResponseDto responseDto = new GameStartRestartResponseDto(
                "session-1", null, 3, 0, GameStatus.IN_PROGRESS, null);

        when(gameService.startGame()).thenReturn(responseDto);

        mockMvc.perform(post("/api/game/start"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value("session-1"))
                .andExpect(jsonPath("$.remainingLives").value(3))
                .andExpect(jsonPath("$.questionCountInRound").value(0))
                .andExpect(jsonPath("$.gameStatus").value("IN_PROGRESS"));

        verify(gameService).startGame();
    }

    /**
     * Verifies that the state endpoint returns the current game state.
     */
    @Test
    public void getGameStateShouldReturnCurrentSessionState() throws Exception {
        GameStateDto responseDto = new GameStateDto(
                "session-1", PersonalityType.DEFAULT, 2, 3,
                GameStatus.IN_PROGRESS, "Is your character fictional?",
                "Is your character fictional?", "Batman");

        when(gameService.getGameState("session-1")).thenReturn(responseDto);

        mockMvc.perform(get("/api/game/session-1/state"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value("session-1"))
                .andExpect(jsonPath("$.personalityType").value("DEFAULT"))
                .andExpect(jsonPath("$.remainingLives").value(2))
                .andExpect(jsonPath("$.questionCountInRound").value(3))
                .andExpect(jsonPath("$.gameStatus").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.lastQuestion").value("Is your character fictional?"))
                .andExpect(jsonPath("$.lastAiMessage").value("Is your character fictional?"))
                .andExpect(jsonPath("$.finalGuess").value("Batman"));

        verify(gameService).getGameState("session-1");
    }

    /**
     * Verifies that the personality endpoint accepts a valid request and returns the updated state.
     */
    @Test
    public void choosePersonalityShouldReturnUpdatedSessionState() throws Exception {
        ChoosePersonalityRequestDto requestDto = new ChoosePersonalityRequestDto(PersonalityType.SARCASTIC);
        GameStateDto responseDto = new GameStateDto(
                "session-1", PersonalityType.SARCASTIC, 3, 0,
                GameStatus.IN_PROGRESS, null, null, null);

        when(gameService.choosePersonality(eq("session-1"), any(ChoosePersonalityRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(patch("/api/game/session-1/personality")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value("session-1"))
                .andExpect(jsonPath("$.personalityType").value("SARCASTIC"))
                .andExpect(jsonPath("$.gameStatus").value("IN_PROGRESS"));

        verify(gameService).choosePersonality(eq("session-1"), any(ChoosePersonalityRequestDto.class));
    }

    /**
     * Verifies that personality requests missing the required field are rejected.
     */
    @Test
    public void choosePersonalityShouldReturnBadRequestWhenPersonalityIsMissing() throws Exception {
        mockMvc.perform(patch("/api/game/session-1/personality")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifies that the answer endpoint returns the updated game state for a valid answer.
     */
    @Test
    public void processAnswerShouldReturnUpdatedSessionState() throws Exception {
        AnswerRequestDto requestDto = new AnswerRequestDto(AnswerType.YES);
        GameStateDto responseDto = new GameStateDto(
                "session-1", PersonalityType.DEFAULT, 3, 1,
                GameStatus.IN_PROGRESS, "Is your character human?",
                "Is your character human?", null);

        when(gameService.processAnswer(eq("session-1"), any(AnswerRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/game/session-1/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value("session-1"))
                .andExpect(jsonPath("$.questionCountInRound").value(1))
                .andExpect(jsonPath("$.gameStatus").value("IN_PROGRESS"));

        verify(gameService).processAnswer(eq("session-1"), any(AnswerRequestDto.class));
    }

    /**
     * Verifies that answer requests missing the required field are rejected.
     */
    @Test
    public void processAnswerShouldReturnBadRequestWhenAnswerTypeIsMissing() throws Exception {
        mockMvc.perform(post("/api/game/session-1/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifies that the restart endpoint returns the reset session state.
     */
    @Test
    public void restartGameShouldReturnResetSessionState() throws Exception {
        GameStartRestartResponseDto responseDto = new GameStartRestartResponseDto(
                "session-1", null, 3, 0, GameStatus.IN_PROGRESS, null);

        when(gameService.restartGame("session-1")).thenReturn(responseDto);

        mockMvc.perform(post("/api/game/session-1/restart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value("session-1"))
                .andExpect(jsonPath("$.remainingLives").value(3))
                .andExpect(jsonPath("$.questionCountInRound").value(0))
                .andExpect(jsonPath("$.gameStatus").value("IN_PROGRESS"));

        verify(gameService).restartGame("session-1");
    }

    /**
     * Verifies that the end endpoint returns the final ended state.
     */
    @Test
    public void endGameShouldReturnEndedSessionState() throws Exception {
        EndGameResponseDto responseDto = new EndGameResponseDto("session-1", GameStatus.ENDED);

        when(gameService.endGame("session-1")).thenReturn(responseDto);

        mockMvc.perform(post("/api/game/session-1/end"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value("session-1"))
                .andExpect(jsonPath("$.gameStatus").value("ENDED"));

        verify(gameService).endGame("session-1");
    }

    /**
     * Verifies that the next-step endpoint returns the mapped AI decision.
     */
    @Test
    public void getNextStepShouldReturnAiDecisionPayload() throws Exception {
        NextStepResponseDto responseDto = new NextStepResponseDto(AiDecisionType.QUESTION, "Is your character human?");

        when(gameService.getNextStep("session-1")).thenReturn(responseDto);

        mockMvc.perform(get("/api/game/session-1/next-step"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("QUESTION"))
                .andExpect(jsonPath("$.content").value("Is your character human?"));

        verify(gameService).getNextStep("session-1");
    }
}
