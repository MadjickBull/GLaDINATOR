function toggleButton() {

  const toggle = document.createElement("label");
  const input = document.createElement("input");
  const span = document.createElement("span")


  toggle.id = "toggle-button";
  input.type = "checkbox";
  input.id = "personality-toggle";
  span.classList.add("slider");


  toggle.classList.add("toggle", "btn-toggle");


    toggle.appendChild(input);
    toggle.appendChild(span);

  return toggle;
}

export { toggleButton };
