function startButton(onClick) {

  const btn = document.createElement("button");

  btn.id = "start-button"
  btn.textContent = "Start";
  btn.classList.add("btn", "btn-start");



  return btn;
}

export { startButton };