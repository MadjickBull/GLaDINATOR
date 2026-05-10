package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.dtos.request.AnswerRequestDto;
import com.codeforall.online.gladinator.dtos.request.ChoosePersonalityRequestDto;
import com.codeforall.online.gladinator.dtos.response.EndGameResponseDto;
import com.codeforall.online.gladinator.dtos.response.GameStartRestartResponseDto;
import com.codeforall.online.gladinator.dtos.response.GameStateDto;
import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;

/**
 * Common interface for game services, provides methods to manage
 * the session flow and core game actions.
 */
public interface GameService {

    /**
     * Starts a new game session.
     *
     * @return the DTO containing the initial state of the created session
     */
    GameStartRestartResponseDto startGame();

    /**
     * Gets the current state of a game session.
     *
     * @param sessionId the session identifier
     * @return the DTO representing the current session state
     */
    GameStateDto getGameState(String sessionId);

    /**
     * Chooses or updates the AI personality for the given session.
     *
     * @param sessionId the session identifier
     * @param requestDto the request DTO containing the selected personality
     * @return the DTO representing the updated session state
     */
    GameStateDto choosePersonality(String sessionId, ChoosePersonalityRequestDto requestDto);

    /**
     * Processes the user's answer for the current AI question or guess.
     *
     * @param sessionId the session identifier
     * @param requestDto the request DTO containing the user's answer
     * @return the DTO representing the updated session state
     */
    GameStateDto processAnswer(String sessionId, AnswerRequestDto requestDto);

    /**
     * Restarts an existing game session.
     *
     * @param sessionId the session identifier
     * @return the DTO containing the reset session state
     */
    GameStartRestartResponseDto restartGame(String sessionId);

    /**
     * Ends a game session manually.
     *
     * @param sessionId the session identifier
     * @return the DTO containing the final ended state
     */
    EndGameResponseDto endGame(String sessionId);

    /**
     * Gets the next AI step for the given session.
     *
     * @param sessionId the session identifier
     * @return the DTO containing the next AI step
     */
    NextStepResponseDto getNextStep(String sessionId);
}
