/**
 * Enumeration types used by the game domain.
 *
 * This package contains enums that define the fixed categorical values used
 * across the application.
 *
 * Main enums:
 *
 * AiDecisionType
 * - defines the type of decision returned by the AI;
 * - possible values include question, guess, and final message.
 *
 * AnswerType
 * - defines the possible answers given by the user;
 * - used to respond to AI questions and guesses.
 *
 * GameStatus
 * - defines the lifecycle state of a game session;
 * - indicates whether the game is still in progress, won by the AI,
 *   won by the player, or manually ended.
 *
 * PersonalityType
 * - defines the available AI personalities;
 * - also provides the descriptive prompt text associated with each personality.
 *
 * Responsibility overview:
 * - these enums constrain the valid domain values;
 * - they help keep game flow and AI behavior explicit and consistent.
 *
 * -----------------------------------------------------------------------------
 *
 * Tipos enumerados usados no domínio do jogo.
 *
 * Este package contém enums que definem os valores categóricos fixos usados
 * em toda a aplicação.
 *
 * Enums principais:
 *
 * AiDecisionType
 * - define o tipo de decisão devolvida pela IA;
 * - os valores possíveis incluem pergunta, guess e mensagem final.
 *
 * AnswerType
 * - define as respostas possíveis dadas pelo utilizador;
 * - é usado para responder a perguntas e guesses da IA.
 *
 * GameStatus
 * - define o estado do ciclo de vida de uma sessão de jogo;
 * - indica se o jogo ainda está em progresso, foi ganho pela IA,
 *   foi ganho pelo jogador ou foi terminado manualmente.
 *
 * PersonalityType
 * - define as personalidades disponíveis da IA;
 * - também fornece o texto descritivo de prompt associado a cada personalidade.
 *
 * Em termos de responsabilidade:
 * - estes enums restringem os valores válidos do domínio;
 * - ajudam a manter o fluxo do jogo e o comportamento da IA
 *   explícitos e consistentes.
 */
package com.codeforall.online.gladinator.model.enums;
