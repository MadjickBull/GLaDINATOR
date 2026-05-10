/**
 * Application services.
 *
 * This package contains the main backend logic and defines the responsibilities
 * related to session management, game flow, and AI behavior.
 *
 * The services act as the bridge between:
 * - the controllers, which receive HTTP requests;
 * - the storage layer, which keeps sessions in memory;
 * - the models, which represent the game state;
 * - the AI layer, which generates questions, guesses, and final messages.
 *
 * Main services:
 *
 * SessionService
 * - creates sessions;
 * - retrieves sessions;
 * - updates sessions;
 * - deletes sessions;
 * - checks whether a session exists.
 *
 * GameService
 * - starts and restarts the game;
 * - starts new sessions with the default AI personality;
 * - changes the AI personality during the session when requested;
 * - processes the user's answers;
 * - controls lives and round progression;
 * - ends the game;
 * - requests the next AI step.
 *
 * AiService
 * - generates the AI next step;
 * - receives the current session context;
 * - returns questions, guesses, and final messages;
 * - provides structured AI decisions to the backend;
 * - integrates OpenAI to generate game output.
 *
 * Responsibility overview:
 *
 * SessionService
 * - handles the technical management of sessions;
 * - works directly with in-memory storage.
 *
 * GameService
 * - handles the business logic of the game;
 * - decides how the session state evolves during play.
 *
 * AiService
 * - handles the textual behavior of the AI;
 * - generates content, but does not control the game rules.
 *
 * -----------------------------------------------------------------------------
 *
 * Services da aplicação.
 *
 * Este package contém a lógica principal do backend e define as responsabilidades
 * associadas à gestão de sessões, ao fluxo do jogo e ao comportamento da IA.
 *
 * Os services fazem a ponte entre:
 * - os controllers, que recebem pedidos HTTP;
 * - o storage, que guarda sessões em memória;
 * - os models, que representam o estado do jogo;
 * - a camada de IA, que gera perguntas, guesses e mensagens finais.
 *
 * Services principais:
 *
 * SessionService
 * - cria sessões;
 * - obtém sessões;
 * - atualiza sessões;
 * - apaga sessões;
 * - verifica a existência de uma sessão.
 *
 * GameService
 * - inicia e reinicia o jogo;
 * - inicia novas sessões com a personalidade default da IA;
 * - permite alterar a personalidade da IA durante a sessão;
 * - processa as respostas do utilizador;
 * - controla vidas e progressão da ronda;
 * - termina o jogo;
 * - pede o próximo passo da IA.
 *
 * AiService
 * - gera o próximo passo da IA;
 * - recebe o contexto atual da sessão;
 * - devolve perguntas, guesses e mensagens finais;
 * - fornece decisões estruturadas ao backend;
 * - integra a OpenAI para gerar o conteúdo do jogo.
 *
 * Em termos de responsabilidade:
 *
 * SessionService
 * - trata da gestão técnica das sessões;
 * - trabalha diretamente com o armazenamento em memória.
 *
 * GameService
 * - trata da lógica de negócio do jogo;
 * - decide como o estado da sessão evolui ao longo da partida.
 *
 * AiService
 * - trata da lógica associada ao comportamento textual da IA;
 * - gera conteúdo, mas não controla as regras do jogo.
 */
package com.codeforall.online.gladinator.services;
