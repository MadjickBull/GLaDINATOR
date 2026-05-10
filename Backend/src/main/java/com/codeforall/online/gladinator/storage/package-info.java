/**
 * In-memory session storage classes.
 *
 * This package contains the structures used by the backend to store and retrieve
 * game sessions while the application is running.
 *
 * Since the project does not use a database, storage is performed in memory
 * through a Map where:
 * - the key is the sessionId;
 * - the value is the corresponding GameSession.
 *
 * Responsibilities of this package:
 * - store active sessions;
 * - retrieve sessions by ID;
 * - check whether a session exists;
 * - remove finished sessions;
 * - provide access to sessions kept in memory.
 *
 * Main class:
 *
 * InMemorySessionStore
 * - works as a simple in-memory repository;
 * - does not contain business logic;
 * - does not decide game rules;
 * - does not generate questions or guesses;
 * - only stores, returns, updates, and removes sessions.
 *
 * Example:
 *
 * sessionId -> GameSession
 *
 * "abc123" -> current game session
 * "xyz789" -> another independent session
 *
 * Note:
 * Session creation, UUID generation, life updates, question counting,
 * and game-state validation belong to the service layer, not to the store.
 *
 * -----------------------------------------------------------------------------
 *
 * Classes responsáveis pelo armazenamento temporário de sessões em memória.
 *
 * Este package contém as estruturas usadas pelo backend para guardar e recuperar
 * o estado das sessões de jogo enquanto a aplicação está em execução.
 *
 * Como o projeto não utiliza base de dados, o armazenamento é feito em memória,
 * através de um Map onde:
 * - a chave é o sessionId;
 * - o valor é a GameSession correspondente.
 *
 * Responsabilidades deste package:
 * - guardar sessões ativas;
 * - procurar sessões por ID;
 * - verificar se uma sessão existe;
 * - remover sessões terminadas;
 * - disponibilizar acesso às sessões guardadas em memória.
 *
 * Classe principal:
 *
 * InMemorySessionStore
 * - funciona como um repositório simples em memória;
 * - não contém lógica de negócio;
 * - não decide regras do jogo;
 * - não gera perguntas nem guesses;
 * - apenas guarda, devolve, atualiza e remove sessões.
 *
 * Exemplo:
 *
 * sessionId -> GameSession
 *
 * "abc123" -> sessão de jogo atual
 * "xyz789" -> outra sessão independente
 *
 * Nota:
 * A lógica de criação de sessões, geração de UUID, atualização de vidas,
 * contagem de perguntas e validação do estado pertence aos services,
 * não ao store.
 */
package com.codeforall.online.gladinator.storage;
