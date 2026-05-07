package codeforall.com.online.gladinator.services;

import codeforall.com.online.gladinator.dtos.request.AnswerRequestDto;
import codeforall.com.online.gladinator.dtos.request.UpdatePersonalityRequestDto;
import codeforall.com.online.gladinator.dtos.response.EndGameResponseDto;
import codeforall.com.online.gladinator.dtos.response.GameStartRestartResponseDto;
import codeforall.com.online.gladinator.dtos.response.GameStateDto;
import codeforall.com.online.gladinator.dtos.response.NextStepResponseDto;

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
