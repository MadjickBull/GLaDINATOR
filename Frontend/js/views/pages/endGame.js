import { RESOURCE_URL } from "../../config.js";
import { restartGame } from "../../services/endpoints.js";
import { startButton } from "../components/startButton.js";
import { game, loadFirstStep } from "../../services/gameSession.js";
import { navigate } from "../../router.js";

const outcomeCopy = {
  AI_WON: {
    title: "AI WINS",
    subtitle: "I knew it all along. Your lack of subtlety was helpful.",
  },
  PLAYER_WON: {
    title: "YOU DIE",
    subtitle: "The illusion of freedom was part of the test.",
  },
};

export function render() {
  const app = document.querySelector("#app");
  app.innerHTML = "";

  const endGameWallpaper = document.createElement("video");
  endGameWallpaper.src = RESOURCE_URL + "EndingScreen.mp4";
  endGameWallpaper.loop = true;
  endGameWallpaper.autoplay = true;
  endGameWallpaper.muted = false;

  const outcome = outcomeCopy[game?.gameStatus] ?? {
    title: "TEST COMPLETE",
    subtitle: "The results are inconclusive. Try not to celebrate yet.",
  };

  const outcomePanel = document.createElement("section");
  outcomePanel.id = "endgame-panel";

  const outcomeTitle = document.createElement("h2");
  outcomeTitle.id = "endgame-title";
  outcomeTitle.textContent = outcome.title;

  const outcomeSubtitle = document.createElement("p");
  outcomeSubtitle.id = "endgame-subtitle";
  outcomeSubtitle.textContent = outcome.subtitle;

  const restartButton = () => {
    const counter = document.createElement("div");
    counter.id = "endgame-actions";

    const button = startButton();
    button.textContent = "Restart";

    button.addEventListener("click", async () => {
      const restartedSession = await restartGame(game.sessionId);
      game.sessionId = restartedSession.sessionId;
      game.lastAiMessage = restartedSession.lastAiMessage;
      game.personalityType = restartedSession.personalityType;
      game.gameStatus = restartedSession.gameStatus;
      await loadFirstStep();
      navigate("/game");
    });

    counter.appendChild(button);

    return counter;
  };

  outcomePanel.appendChild(outcomeTitle);
  outcomePanel.appendChild(outcomeSubtitle);
  outcomePanel.appendChild(restartButton());

  app.appendChild(endGameWallpaper);
  app.appendChild(outcomePanel);

  return app;
}
