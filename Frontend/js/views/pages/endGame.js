import { RESOURCE_URL } from "../../config.js";
import { gameStart, restartGame } from "../../services/endpoints.js";
import { startButton } from "../components/startButton.js";
import { game } from "../../services/gameSession.js";
import { navigate } from "../../router.js";

export function render() {
  const app = document.querySelector("#app");
  app.innerHTML = "";

  const endGameWallpaper = document.createElement("video");
  endGameWallpaper.src = RESOURCE_URL + "EndingScreen.mp4";
  endGameWallpaper.loop = true;
  endGameWallpaper.autoplay = true;
  endGameWallpaper.muted = false;

  const restartButton = () => {
    const counter = document.createElement("div");

    const button = startButton();
    button.textContent = "Restart";

    button.addEventListener("click", async () => {
      const newSession = await gameStart();
      game.sessionId = newSession.sessionId;
      game.lastAiMessage = newSession.lastAiMessage;
      game.personalityType = newSession.personalityType;
      navigate("/game");
    });

    counter.appendChild(button);

    return counter;
  };

  app.appendChild(restartButton());
  app.appendChild(endGameWallpaper);

  return app;
}
