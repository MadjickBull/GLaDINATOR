package com.codeforall.online.gladinator.services;

import com.codeforall.online.gladinator.dtos.request.AnswerRequestDto;
import com.codeforall.online.gladinator.dtos.request.UpdatePersonalityRequestDto;
import com.codeforall.online.gladinator.dtos.response.EndGameResponseDto;
import com.codeforall.online.gladinator.dtos.response.GameStartRestartResponseDto;
import com.codeforall.online.gladinator.dtos.response.GameStateDto;
import com.codeforall.online.gladinator.dtos.response.NextStepResponseDto;

//lógica do jogo
public interface GameService {

    GameStartRestartResponseDto startGame();

    GameStateDto getGameState(String sessionId);

    GameStateDto updatePersonality(String sessionId, UpdatePersonalityRequestDto requestDto);

    GameStateDto processAnswer(String sessionId, AnswerRequestDto requestDto);

    GameStartRestartResponseDto restartGame(String sessionId);

    EndGameResponseDto endGame(String sessionId);

    NextStepResponseDto getNextStep(String sessionId);

}
