import { RESOURCE_URL } from "../../config.js";
import { startButton } from "../components/startButton.js";
import { navigate } from "../../router.js";

export function render() {
  const app = document.querySelector("#app");
  app.innerHTML = "";

  const homeWallpaper = document.createElement("video");
  homeWallpaper.src = RESOURCE_URL + "LandingPage.mp4";
  homeWallpaper.loop = true;
  homeWallpaper.autoplay = true;
  homeWallpaper.muted = true;

  const homeTextBox = document.createElement("div");
  const boxText = document.createElement("p");

  homeTextBox.id = "home-text-box";
  boxText.id = "home-text";
  boxText.textContent =
    "Meet GLaDOS — an experimental AI personality inspired by the unpredictable deduction style of Akinator, built to analyze behavior, adapt responses, and evolve through constant testing. The project is currently in active development, with ongoing fixes for logic inconsistencies, routing bugs, and interaction systems as new features are introduced. To help expand development, improve stability, and accelerate future updates, support the project through our Kickstarter crowdfunding campaign.";

  homeTextBox.appendChild(boxText);

  const beginButton = startButton();

  beginButton.addEventListener("click", () => {
    navigate("/game");
  });

  app.appendChild(beginButton);
  app.appendChild(homeTextBox);
  app.appendChild(homeWallpaper);

  return app;
}
