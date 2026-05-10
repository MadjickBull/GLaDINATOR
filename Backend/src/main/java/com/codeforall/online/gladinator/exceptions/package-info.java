/**
 * Application-specific exceptions.
 *
 * This package contains the custom runtime exceptions used to represent
 * domain and integration errors in the backend.
 *
 * Main exceptions:
 *
 * GameSessionNotFoundException
 * - thrown when a requested game session does not exist.
 *
 * InvalidGameStateException
 * - thrown when an operation is not valid for the current game state.
 *
 * AiIntegrationException
 * - thrown when the AI integration layer fails to generate a valid response.
 *
 * Responsibility overview:
 * - these exceptions make failures more explicit than generic runtime errors;
 * - they help distinguish session errors, game-state errors, and AI integration failures.
 *
 * -----------------------------------------------------------------------------
 *
 * Exceptions específicas da aplicação.
 *
 * Este package contém as exceções runtime personalizadas usadas para representar
 * erros de domínio e de integração no backend.
 *
 * Exceptions principais:
 *
 * GameSessionNotFoundException
 * - lançada quando uma sessão pedida não existe.
 *
 * InvalidGameStateException
 * - lançada quando uma operação não é válida para o estado atual do jogo.
 *
 * AiIntegrationException
 * - lançada quando a camada de integração com a IA falha ao gerar uma resposta válida.
 *
 * Em termos de responsabilidade:
 * - estas exceptions tornam as falhas mais explícitas do que erros runtime genéricos;
 * - ajudam a distinguir erros de sessão, erros de estado do jogo e falhas de integração com a IA.
 */
package com.codeforall.online.gladinator.exceptions;

