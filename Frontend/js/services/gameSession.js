import { gameStart, getNextStep } from "./endpoints.js";

export let game = null;

export async function initGame() {
  game = await gameStart();
  game.gameStatus = game.gameStatus ?? "IN_PROGRESS";
  return loadFirstStep();
}

export async function loadFirstStep() {
  const firstStep = await getNextStep(game.sessionId);
  game.lastAiMessage = firstStep.content;
  return game;
}
