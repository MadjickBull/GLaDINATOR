import { yesButton } from "../components/yesButton.js";
import { noButton } from "../components/noButton.js";
import {
  speak,
  sendAnswer,
  getNextStep,
  getState,
} from "../../services/endpoints.js";
import { game } from "../../services/gameSession.js";
import { RESOURCE_URL } from "../../config.js";
import { navigate } from "../../router.js";

const wallpaperMap = {
  LOVER: "FlirtatousGladinator.mp4",
  DEFAULT: "GLaDOSNormal.mp4",
  SARCASTIC: "GLaDOSAngry.mp4",
};

export async function render() {
  const app = document.querySelector("#app");
  app.innerHTML = "";

  speak(game.lastAiMessage);

  const rulesBox = document.createElement("div");
  const rulesText = document.createElement("p");

  rulesBox.id = "game-text-box";
  rulesText.id = "game-text";
  rulesText.textContent =
    "Think of something, test subject. I will ask the questions. You will answer them. Eventually, I will guess correctly and you will die. And if I do not guess correctly, you will still die. Just knowing you briefly inconvenienced a superintelligent AI. Congratulations.";
  rulesBox.appendChild(rulesText);

  const counterContainer = document.createElement("div");
  counterContainer.id = "counter-container";

  const counterDescription = document.createElement("h4");
  counterDescription.id = "counterDescription";
  counterDescription.textContent = "Remaining Guesses";

  const counter = document.createElement("div");
  counter.id = "guess-counter";
  counter.textContent = game.remainingLives;

  counterContainer.appendChild(counterDescription);
  counterContainer.appendChild(counter);

  const gameWallpaper = document.createElement("video");
  gameWallpaper.loop = true;
  gameWallpaper.autoplay = true;
  gameWallpaper.muted = true;
  gameWallpaper.src =
    RESOURCE_URL + (wallpaperMap[game.personalityType] ?? wallpaperMap.DEFAULT);

  app.appendChild(gameWallpaper);

  const wallPaperChange = () => {
    gameWallpaper.src =
      RESOURCE_URL +
      (wallpaperMap[game.personalityType] || wallpaperMap.DEFAULT);
  };
  document.documentElement.addEventListener(
    "personalitychange",
    wallPaperChange,
  );

  const aiMessageBox = document.createElement("div");
  aiMessageBox.id = "message-box";

  const aiMessage = document.createElement("p");
  aiMessage.id = "ai-message";
  aiMessage.textContent = game.lastAiMessage;

  aiMessageBox.appendChild(aiMessage);

  const buttonY = yesButton();
  const buttonN = noButton();

  async function handleAnswer(answer) {
    const sendRes = await sendAnswer(game.sessionId, answer);

    game.gameStatus = sendRes.gameStatus;
    game.lastAiMessage = sendRes.lastAiMessage ?? game.lastAiMessage;

    if (
      sendRes.gameStatus === "AI_WON" ||
      sendRes.gameStatus === "PLAYER_WON"
    ) {
      navigate("/end");
      return;
    }

    const res = await getNextStep(game.sessionId);

    const state = await getState(game.sessionId);

    game.remainingLives = state.remainingLives;
    counter.textContent = game.remainingLives;

    game.lastAiMessage = res.content;
    aiMessage.textContent = res.content;

    speak(res.content);
  }

  buttonY.addEventListener("click", () => handleAnswer(buttonY.textContent));
  buttonN.addEventListener("click", () => handleAnswer(buttonN.textContent));

  app.appendChild(buttonY);
  app.appendChild(buttonN);
  app.appendChild(aiMessageBox);
  app.appendChild(rulesBox);
  app.appendChild(counterContainer);

  return app;
}
