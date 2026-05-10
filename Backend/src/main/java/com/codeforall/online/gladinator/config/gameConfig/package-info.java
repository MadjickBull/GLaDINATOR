/**
 * Spring configuration classes for game-level beans.
 *
 * This package contains Spring configuration classes responsible for creating
 * beans related to the fixed game configuration.
 *
 * Main class:
 *
 * GameBeansConfig
 * - creates the GameConfig bean used across the application;
 * - centralizes the fixed rules of the game, such as initial lives
 *   and maximum questions per round.
 *
 * Responsibility overview:
 * - this package belongs to the application configuration layer;
 * - it wires technical Spring beans, not business logic.
 *
 * -----------------------------------------------------------------------------
 *
 * Classes de configuração Spring para beans do jogo.
 *
 * Este package contém classes de configuração Spring responsáveis por criar
 * beans relacionados com a configuração fixa do jogo.
 *
 * Classe principal:
 *
 * GameBeansConfig
 * - cria o bean GameConfig usado em toda a aplicação;
 * - centraliza as regras fixas do jogo, como o número inicial de vidas
 *   e o número máximo de perguntas por ronda.
 *
 * Em termos de responsabilidade:
 * - este package pertence à camada de configuração da aplicação;
 * - faz o wiring técnico de beans Spring, não lógica de negócio.
 */
package com.codeforall.online.gladinator.config.gameConfig;