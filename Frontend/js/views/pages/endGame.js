import { RESOURCE_URL } from "../../config.js";

export function render() {
  const app = document.querySelector("#app");
  app.innerHTML = "";

  const endGameWallpaper = document.createElement("video");
  endGameWallpaper.src = RESOURCE_URL + "EndigScreen.mp4";
  endGameWallpaper.loop = true;
  endGameWallpaper.autoplay = true;
  endGameWallpaper.muted = true;

  app.appendChild(endGameWallpaper);

  return app;
}
