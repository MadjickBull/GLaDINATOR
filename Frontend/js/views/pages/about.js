import { RESOURCE_URL } from "../../config.js";

export function render() {
  const app = document.querySelector("#app");
  app.innerHTML = "";

  return app;
}
