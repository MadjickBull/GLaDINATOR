/**
 * Response DTOs of the application.
 *
 * This package contains the classes that represent the data sent from the backend
 * to the frontend through the REST API.
 *
 * Response DTOs are used to:
 * - transport the current game state;
 * - return the AI next step;
 * - inform the frontend about the start, restart, or end of a session.
 *
 * Main DTOs:
 *
 * GameStartRestartResponseDto
 * - used in the responses of the start and restart endpoints;
 * - contains the initial or reset state of the session.
 *
 * GameStateDto
 * - represents the complete current state of the session;
 * - includes information such as lives, personality, last question,
 *   last AI message, and final guess.
 *
 * NextStepResponseDto
 * - represents the next step returned by the AI;
 * - includes the decision type (QUESTION, GUESS, or FINAL_MESSAGE)
 *   and the corresponding text content.
 *
 * EndGameResponseDto
 * - represents the response of the end-session endpoint;
 * - informs the frontend about the ended session and its final status.
 *
 * Example JSON:
 *
 * GameStartRestartResponseDto
 * {
 *   "sessionId": "abc123",
 *   "personalityType": "DEFAULT",
 *   "remainingLives": 3,
 *   "questionCountInRound": 0,
 *   "gameStatus": "IN_PROGRESS"
 * }
 *
 * NextStepResponseDto
 * {
 *   "type": "QUESTION",
 *   "content": "Is your character human?"
 * }
 *
 * -----------------------------------------------------------------------------
 *
 * DTOs de response da aplicação.
 *
 * Este package contém classes que representam os dados que saem do backend
 * e são recebidos pelo frontend através da API REST.
 *
 * Os response DTOs servem para:
 * - transportar o estado atual do jogo;
 * - devolver o próximo passo da IA;
 * - informar o frontend sobre o início, reinício ou fim de uma sessão.
 *
 * DTOs principais:
 *
 * GameStartRestartResponseDto
 * - usado nas respostas dos endpoints de start e restart;
 * - contém o estado inicial ou reiniciado da sessão.
 *
 * GameStateDto
 * - representa o estado atual completo da sessão;
 * - inclui informação como vidas, personalidade, última pergunta,
 *   última mensagem da IA e guess final.
 *
 * NextStepResponseDto
 * - representa o próximo passo devolvido pela IA;
 * - inclui o tipo da decisão (QUESTION, GUESS ou FINAL_MESSAGE)
 *   e o conteúdo textual correspondente.
 *
 * EndGameResponseDto
 * - representa a resposta do endpoint de fim de sessão;
 * - informa o frontend sobre a sessão terminada e o respetivo estado final.
 *
 * Exemplos de JSON:
 *
 * GameStartRestartResponseDto
 * {
 *   "sessionId": "abc123",
 *   "personalityType": "DEFAULT",
 *   "remainingLives": 3,
 *   "questionCountInRound": 0,
 *   "gameStatus": "IN_PROGRESS"
 * }
 *
 * NextStepResponseDto
 * {
 *   "type": "QUESTION",
 *   "content": "Is your character human?"
 * }
 */
package com.codeforall.online.gladinator.dtos.response;
