import {
  gameStart,
  getNextStep,
  getState,
  sendAnswer,
  changePersonality,
  restartGame,
  endGame,
  getInfo,
  speak,
} from "../services/endpoints.js";

// ── DOM refs ───────────────────────────────────────────────────────────────
const inputSessionId = document.getElementById("input-session-id");
const inputPersonality = document.getElementById("input-personality");
const inputAnswer = document.getElementById("input-answer");
const chatbox = document.getElementById("chatbox");

// ── Helpers ────────────────────────────────────────────────────────────────
function log(msg, type = "info") {
  const colors = { info: "#7ec8e3", ok: "#90ee90", error: "#ff6b6b" };
  const line = document.createElement("div");
  line.style.color = colors[type] ?? "#eee";
  line.textContent = `[${new Date().toLocaleTimeString()}] ${msg}`;
  chatbox.appendChild(line);
  chatbox.scrollTop = chatbox.scrollHeight;
}

function sessionId() {
  const id = inputSessionId.value.trim();
  if (!id) {
    log("No session ID set", "error");
    return null;
  }
  return id;
}

function personality() {
  return inputPersonality.value; // already ALL_CAPS from <option value>
}

// ── Button wiring ──────────────────────────────────────────────────────────
document
  .getElementById("btn-game-start")
  .addEventListener("click", async () => {
    log("gameStart() →");
    try {
      const res = await gameStart();
      inputSessionId.value = res.sessionId;
      log(
        `OK → sessionId: ${res.sessionId} | status: ${res.gameStatus} | lives: ${res.remainingLives}`,
        "ok",
      );
      log(`AI: ${res.lastAiMessage}`, "ok");
    } catch (e) {
      log(`ERROR → ${e.message}`, "error");
    }
  });

document.getElementById("btn-next-step").addEventListener("click", async () => {
  const id = sessionId();
  if (!id) return;
  log(`getNextStep(${id}) →`);
  try {
    const res = await getNextStep(id);
    log(`OK → ${JSON.stringify(res)}`, "ok");
  } catch (e) {
    log(`ERROR → ${e.message}`, "error");
  }
});

document.getElementById("btn-get-state").addEventListener("click", async () => {
  const id = sessionId();
  if (!id) return;
  log(`getState(${id}) →`);
  try {
    const res = await getState(id);
    log(`OK → ${JSON.stringify(res)}`, "ok");
  } catch (e) {
    log(`ERROR → ${e.message}`, "error");
  }
});

document
  .getElementById("btn-send-answer")
  .addEventListener("click", async () => {
    const id = sessionId();
    if (!id) return;
    const answer = inputAnswer.value.trim();
    if (!answer) return log("No answer typed", "error");
    log(`sendAnswer(${id}, "${answer}") →`);
    try {
      const res = await sendAnswer(id, answer);
      log(`OK → ${JSON.stringify(res)}`, "ok");
    } catch (e) {
      log(`ERROR → ${e.message}`, "error");
    }
  });

document
  .getElementById("btn-change-personality")
  .addEventListener("click", async () => {
    const id = sessionId();
    if (!id) return;
    const p = personality();
    log(`changePersonality(${id}, ${p}) →`);
    try {
      const res = await changePersonality(id, p);
      log(`OK → personalityType: ${res.personalityType}`, "ok");
    } catch (e) {
      log(`ERROR → ${e.message}`, "error");
    }
  });

document.getElementById("btn-restart").addEventListener("click", async () => {
  const id = sessionId();
  if (!id) return;
  log(`restartGame(${id}) →`);
  try {
    const res = await restartGame(id);
    log(`OK → ${JSON.stringify(res)}`, "ok");
  } catch (e) {
    log(`ERROR → ${e.message}`, "error");
  }
});

document.getElementById("btn-end").addEventListener("click", async () => {
  const id = sessionId();
  if (!id) return;
  log(`endGame(${id}) →`);
  try {
    const res = await endGame(id);
    log(`OK → ${JSON.stringify(res)}`, "ok");
  } catch (e) {
    log(`ERROR → ${e.message}`, "error");
  }
});

document.getElementById("btn-ai-info").addEventListener("click", async () => {
  log("getInfo() →");
  try {
    const res = await getInfo();
    log(`OK → ${JSON.stringify(res)}`, "ok");
  } catch (e) {
    log(`ERROR → ${e.message}`, "error");
  }
});

document.getElementById("btn-speak").addEventListener("click", async () => {
  const id = sessionId();
  if (!id) return;
  log("speak() — reading lastAiMessage from state →");
  try {
    const state = await getState(id);
    const msg = state.lastAiMessage;
    if (!msg) return log("No lastAiMessage in state", "error");
    log(`Speaking: "${msg}"`);
    const blob = await speak(msg);
    const url = URL.createObjectURL(blob);
    new Audio(url).play();
    log("Audio playing...", "ok");
  } catch (e) {
    log(`ERROR → ${e.message}`, "error");
  }
});

document.getElementById("btn-clear").addEventListener("click", () => {
  chatbox.innerHTML = "";
});

