import { gameStart } from "./endpoints.js";

export let game = null;

export async function initGame() {
  game = await gameStart();

  return game;
}

export function setPersonality(value) {
  game.personalityType = value;
}
