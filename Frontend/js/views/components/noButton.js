function noButton(onClick) {

  const btn = document.createElement("button");

  btn.id = "no-button"
  btn.textContent = "NO";
  btn.classList.add("btn", "btn-no");



  return btn;
}

export { noButton };