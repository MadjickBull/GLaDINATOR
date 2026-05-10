import {
  GAME_RESTART,
  GAME_START,
  GAME_STATE,
  GAME_NEXT_STEP,
  GAME_ANSWER,
  GAME_CHANGE_PERSONALITY,
  INFO,
  GLADOS_TTS_API,
  GAME_END,
} from "../config.js";

// START GAME   / personalityType  and "DEFAULT" on all caps
export async function gameStart(personality) {
  const resp = await fetch(GAME_START, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(personality),
  });
  return resp.json();
}

//RESTART GAME
export async function restartGame(sessionId) {
  const resp = await fetch(GAME_RESTART(sessionId), { method: "POST" });
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
    body: JSON.stringify({ answerType: answer }),
  });
  return resp.json();
}

//END GAME

export async function endGame(sessionId) {
  const resp = await fetch(GAME_END(sessionId), { method: "POST" });
  return resp.json();
}

// GET STATE
export async function getState(sessionId) {
  const resp = await fetch(GAME_STATE(sessionId));
  return resp.json();
}

// CHANGE PERSONALITY // personalityType  "DEFAULT" value all caps aswell
export async function changePersonality(sessionId, personality) {
  const resp = await fetch(GAME_CHANGE_PERSONALITY(sessionId), {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ personalityType: personality }),
  });
  return resp.json();
}

// AI INFO
export async function getInfo() {
  const resp = await fetch(INFO);
  return resp.json();
}

//Glados Text-to-Speech ;
export async function speak(lastAiMessage) {
  const rawAudio = await fetch(
    GLADOS_TTS_API + encodeURIComponent(lastAiMessage),
  );
  const blob = await rawAudio.blob();
  const url = URL.createObjectURL(blob);
  new Audio(url).play();
}
