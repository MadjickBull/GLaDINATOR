/**
 * Converter components of the application.
 *
 * This package contains the converter classes responsible for transforming
 * internal backend models into DTOs returned by the REST API.
 *
 * These converters help:
 * - keep controllers and services free from repetitive mapping code;
 * - separate internal domain models from external API representations;
 * - make response construction more explicit and reusable.
 *
 * Main converters:
 *
 * AiDecisionToNextStepResponseDto
 * - converts an internal AiDecision into the DTO returned by the next-step endpoint.
 *
 * GameSessionToEndGameResponseDto
 * - converts a GameSession into the DTO returned when a session is manually ended.
 *
 * GameSessionToGameStartRestartResponseDto
 * - converts a GameSession into the DTO returned by the start and restart endpoints.
 *
 * GameSessionToGameStateDto
 * - converts a GameSession into the DTO representing the current full game state.
 *
 * Responsibility overview:
 * - converters do not contain business logic;
 * - they only transform internal objects into API response structures.
 *
 * -----------------------------------------------------------------------------
 *
 * Components converter da aplicação.
 *
 * Este package contém as classes converter responsáveis por transformar
 * os modelos internos do backend em DTOs devolvidos pela API REST.
 *
 * Estes converters ajudam a:
 * - manter controllers e services livres de código repetitivo de mapeamento;
 * - separar os modelos internos do domínio das representações externas da API;
 * - tornar a construção das respostas mais explícita e reutilizável.
 *
 * Converters principais:
 *
 * AiDecisionToNextStepResponseDto
 * - converte uma AiDecision interna no DTO devolvido pelo endpoint next-step.
 *
 * GameSessionToEndGameResponseDto
 * - converte uma GameSession no DTO devolvido quando uma sessão é terminada manualmente.
 *
 * GameSessionToGameStartRestartResponseDto
 * - converte uma GameSession no DTO devolvido pelos endpoints de start e restart.
 *
 * GameSessionToGameStateDto
 * - converte uma GameSession no DTO que representa o estado completo atual do jogo.
 *
 * Em termos de responsabilidade:
 * - os converters não contêm lógica de negócio;
 * - apenas transformam objetos internos em estruturas de resposta da API.
 */
package com.codeforall.online.gladinator.converters;