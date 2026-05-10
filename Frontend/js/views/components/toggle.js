import { game } from "../../services/gameSession.js";
import { changePersonality } from "../../services/endpoints.js";

export function toggleButton() {
  const container = document.createElement("div");
  container.classList.add("personality-switcher");

  const btnLover = document.createElement("button");
  btnLover.textContent = "♥︎";
  btnLover.dataset.value = "LOVER";
  container.appendChild(btnLover);

  const btnDefault = document.createElement("button");
  btnDefault.textContent = "🤖";
  btnDefault.dataset.value = "DEFAULT";
  btnDefault.classList.add("active");
  container.appendChild(btnDefault);

  const btnSarcastic = document.createElement("button");
  btnSarcastic.textContent = "⚔︎";
  btnSarcastic.dataset.value = "SARCASTIC";
  container.appendChild(btnSarcastic);

  container.addEventListener("click", async (e) => {
    const value = e.target.dataset.value;
    if (!value) return;

    document.documentElement.setAttribute("data-personality", value);
    game.personalityType = value;

    document.documentElement.dispatchEvent(
      new CustomEvent("personalitychange"),
    );

    container
      .querySelectorAll("button")
      .forEach((btn) => btn.classList.remove("active"));
    e.target.classList.add("active");

    await changePersonality(game.sessionId, value);
  });

  return container;
}
