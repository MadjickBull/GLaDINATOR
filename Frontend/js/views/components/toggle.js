 export function toggleButton() {
  const container = document.createElement("div");
  container.classList.add("personality-switcher")
    const btnLover = document.createElement("button");
    btnLover.textContent = "💕";
    btnLover.dataset.value = "0"
    container.appendChild(btnLover);

    const btnDefault = document.createElement("button");
    btnDefault.textContent = "🤖";
    btnDefault.dataset.value = "1"
    container.appendChild(btnDefault);

    const btnSarcastic = document.createElement("button");
    btnSarcastic.textContent = "🔪";
    btnSarcastic.dataset.value = "2"
    container.appendChild(btnSarcastic);

  return container;
}