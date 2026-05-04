import {
  GAME_ANSWER,
  GAME_NEXT_QUESTION,
  GAME_START,
  GAME_STATE,
  INFO,
} from "../config.js";

// START GAME
async function gameStart() {
  const resp = await fetch(GAME_START, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  });

  const respJson = await resp.json();
  return respJson.sessionId;
}

// SEND ANSWER
async function sendAnswer(sessionId, answer) {
  const resp = await fetch(GAME_ANSWER(sessionId), {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(answer),
  });

  return await resp.json();
}

// NEXT QUESTION
async function getQuestion(sessionId) {
  const resp = await fetch(GAME_NEXT_QUESTION(sessionId));
  return await resp.json();
}

// GAME STATE
async function getState(sessionId) {
  const resp = await fetch(GAME_STATE(sessionId));
  return await resp.json();
}

// GET STATE
async function getState(sessionId) {
  const resp = await fetch(GAME_STATE(sessionId));

  return await resp.json();
}

// GET INFO

async function getInfo() {
  const resp = await fetch(INFO);

  return await resp.json();
}
