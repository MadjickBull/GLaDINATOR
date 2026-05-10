import start from "../js/router.js";
import { initGame } from "./services/gameSession.js";
import { toggleButton } from "./views/components/toggle.js";

addEventListener("DOMContentLoaded", async () => {
  const toggleEl = toggleButton();
  const header = document.getElementById("header-right");
  header.appendChild(toggleEl);

  await initGame();
  start();
});
