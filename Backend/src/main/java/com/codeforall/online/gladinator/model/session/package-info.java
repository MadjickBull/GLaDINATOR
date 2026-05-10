/**
 * Session-related model classes.
 *
 * This package contains the core models associated with the runtime state
 * of a GLaDINATOR game session.
 *
 * Together, these classes describe:
 * - the fixed game rules used during play;
 * - the history of answers given by the user;
 * - the current state of each active session.
 *
 * Main classes:
 *
 * GameConfig
 * - represents the fixed rules of the game;
 * - defines values such as the AI initial number of lives
 *   and the maximum number of questions per round.
 *
 * GameAnswer
 * - represents an answer given by the user;
 * - stores the related question, the answer type,
 *   and the order of that question in the session history.
 *
 * GameSession
 * - represents the game session itself;
 * - stores the session identifier, the active personality,
 *   the remaining lives, the number of questions asked in the current round,
 *   the game status, and the answer history;
 * - may also store the last question, the last AI message,
 *   and the last guess made by the AI.
 *
 * Responsibility overview:
 * - GameConfig defines the game rules;
 * - GameAnswer records user interactions;
 * - GameSession aggregates the complete session state.
 *
 * -----------------------------------------------------------------------------
 *
 * Classes de modelo relacionadas com a sessão.
 *
 * Este package contém os modelos principais associados ao estado em execução
 * de uma sessão de jogo do GLaDINATOR.
 *
 * Em conjunto, estas classes descrevem:
 * - as regras fixas do jogo usadas durante a partida;
 * - o histórico de respostas dadas pelo utilizador;
 * - o estado atual de cada sessão ativa.
 *
 * Classes principais:
 *
 * GameConfig
 * - representa as regras fixas do jogo;
 * - define valores como o número inicial de vidas da IA
 *   e o número máximo de perguntas por ronda.
 *
 * GameAnswer
 * - representa uma resposta dada pelo utilizador;
 * - guarda a pergunta associada, o tipo de resposta
 *   e a ordem dessa pergunta no histórico da sessão.
 *
 * GameSession
 * - representa a própria sessão de jogo;
 * - guarda o identificador da sessão, a personalidade ativa,
 *   as vidas restantes, o número de perguntas feitas na ronda atual,
 *   o estado do jogo e o histórico de respostas;
 * - também pode guardar a última pergunta, a última mensagem da IA
 *   e a última guess feita pela IA.
 *
 * Em termos de responsabilidade:
 * - GameConfig define as regras do jogo;
 * - GameAnswer regista as interações do utilizador;
 * - GameSession agrega o estado completo da sessão.
 */
package com.codeforall.online.gladinator.model.session;
