import { RESOURCE_URL } from "../../config.js";
import { startButton } from "../components/startButton.js";

export function render() {
  const app = document.querySelector("#app");
  app.innerHTML = "";

  const homeWallpaper = document.createElement("video");
  homeWallpaper.src = RESOURCE_URL + "LandingPage.mp4";
  homeWallpaper.loop = true;
  homeWallpaper.autoplay = true;
  homeWallpaper.muted = true;

  startButton(() => {});

  app.appendChild(homeWallpaper);

  return app;
}
