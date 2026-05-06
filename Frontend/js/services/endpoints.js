import {
  GAME_START,
  GAME_STATE,
  GAME_NEXT_STEP,
  GAME_ANSWER,
  GAME_CHANGE_PERSONALITY,
  INFO,
} from "../config.js";

// START GAME
export async function gameStart(personality) {
  const resp = await fetch(GAME_START, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ personality }),
  });
  return resp.json();
}

// NEXT STEP
export async function getNextStep(sessionId) {
  const resp = await fetch(GAME_NEXT_STEP(sessionId));
  return resp.json();
}

// SEND ANSWER
export async function sendAnswer(sessionId, answer) {
  const resp = await fetch(GAME_ANSWER(sessionId), {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ answer }),
  });
  return resp.json();
}

// GET STATE
export async function getState(sessionId) {
  const resp = await fetch(GAME_STATE(sessionId));
  return resp.json();
}

// CHANGE PERSONALITY
export async function changePersonality(sessionId, personality) {
  const resp = await fetch(GAME_CHANGE_PERSONALITY(sessionId), {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ personality }),
  });
  return resp.json();
}

// AI INFO
export async function getInfo() {
  const resp = await fetch(INFO);
  return resp.json();
}
