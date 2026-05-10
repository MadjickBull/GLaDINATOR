import { gameStart, getNextStep } from "./endpoints.js";

export let game = null;

export async function initGame() {
  game = await gameStart();
  const firstStep = await getNextStep(game.sessionId);
  game.lastAiMessage = firstStep.content;
  return game;
}
