function yesButton(onClick) {

  const btn = document.createElement("button");

  btn.id = "yes-button"
  btn.textContent = "YES";
  btn.classList.add("btn", "btn-yes");



  return btn;
}

export { yesButton };