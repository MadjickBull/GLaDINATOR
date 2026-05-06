export const BASE_URL = "http://localhost:8080/api/game";
export const INFO = "http://localhost:8080/api/ai/info";
export const RESOURCE_URL = "../js/resources/";

export const GAME_START = `${BASE_URL}/start`;
export const GAME_STATE = (id) => `${BASE_URL}/${id}/state`;
export const GAME_NEXT_STEP = (id) => `${BASE_URL}/${id}/next-step`;
export const GAME_ANSWER = (id) => `${BASE_URL}/${id}/answer`;
export const GAME_CHANGE_PERSONALITY = (id) => `${BASE_URL}/${id}/personality`;
export const GAME_RESTART = (id) => `${BASE_URL}/${id}/restart`;
export const GAME_END = (id) => `${BASE_URL}/${id}/end`;
