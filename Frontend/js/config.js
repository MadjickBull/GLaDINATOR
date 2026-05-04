export const RESOURCE_URL = "/js/resources/";
export const BASE_URL = "https://localhost:8080/api/game/";
export const INFO = "https://localhost:8080/api/ai/info";
export const GAME_START = BASE_URL + "start";

export const GAME_SESSION = (sessionId) => `${BASE_URL}${sessionId}/`;
export const GAME_STATE = (sessionId) => `${BASE_URL}${sessionId}/state`;
export const GAME_ANSWER = (sessionId) => `${BASE_URL}${sessionId}/answer`;
export const GAME_NEXT_QUESTION = (sessionId) =>
  `${BASE_URL}${sessionId}/next-question`;
