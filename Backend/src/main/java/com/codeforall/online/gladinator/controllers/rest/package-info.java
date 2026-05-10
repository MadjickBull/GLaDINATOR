/**
 * REST controllers of the application.
 *
 * This package contains the controllers responsible for exposing the backend REST API.
 *
 * - the frontend sends requests to the API;
 * - the controllers receive those requests;
 * - the services handle the business logic;
 * - the backend returns structured responses.
 *
 * Main controller:
 *
 * RestGameController
 * - exposes the main game endpoints;
 * - allows creating a session, retrieving the current state, choosing the AI personality,
 *   processing answers, restarting the session, ending it, and requesting the AI next step.
 *
 * Main endpoints:
 *
 * POST /api/game/start
 * - creates a new game session with the default AI personality.
 *
 * GET /api/game/{sessionId}/state
 * - returns the current session state.
 *
 * PATCH /api/game/{sessionId}/personality
 * - updates the AI personality during the session.
 *
 * POST /api/game/{sessionId}/answer
 * - processes the user's answer.
 *
 * POST /api/game/{sessionId}/restart
 * - restarts the session while keeping the same sessionId.
 *
 * POST /api/game/{sessionId}/end
 * - manually ends the session.
 *
 * GET /api/game/{sessionId}/next-step
 * - returns the AI next step: question, guess, or final message.
 *
 * -----------------------------------------------------------------------------
 *
 * Controllers REST da aplicação.
 *
 * Este package contém os controllers responsáveis por expor a API REST do backend.
 *
 * - o frontend envia pedidos à API;
 * - os controllers recebem esses pedidos;
 * - os services tratam da lógica de negócio;
 * - o backend devolve respostas estruturadas.
 *
 * Controller principal:
 *
 * RestGameController
 * - expõe os endpoints principais do jogo;
 * - permite criar uma sessão, obter o estado atual, escolher a personalidade da IA,
 *   processar respostas, reiniciar a sessão, terminá-la e pedir o próximo passo da IA.
 *
 * Endpoints principais:
 *
 * POST /api/game/start
 * - cria uma nova sessão de jogo com a personalidade default da IA.
 *
 * GET /api/game/{sessionId}/state
 * - devolve o estado atual da sessão.
 *
 * PATCH /api/game/{sessionId}/personality
 * - atualiza a personalidade da IA durante a sessão.
 *
 * POST /api/game/{sessionId}/answer
 * - processa a resposta do utilizador.
 *
 * POST /api/game/{sessionId}/restart
 * - reinicia a sessão mantendo o mesmo sessionId.
 *
 * POST /api/game/{sessionId}/end
 * - termina manualmente a sessão.
 *
 * GET /api/game/{sessionId}/next-step
 * - devolve o próximo passo da IA: pergunta, guess ou mensagem final.
 */
package com.codeforall.online.gladinator.controllers.rest;
