import start from "../js/router.js";
import { toggleButton } from "./views/components/toggle.js";

addEventListener("DOMContentLoaded", () => {

  const toggleEl = toggleButton();
  document.documentElement.setAttribute("data-personality", "1");
  toggleEl.querySelector("[data-value='1']").classList.add("active");
  toggleEl.addEventListener("click", (e) => {
    
    if (e.target.dataset.value) {
      const value = e.target.dataset.value;
      if (value === "0") {
        document.documentElement.style.setProperty("--accent-color", "#ff69b4");
      } else if (value === "1") {
        document.documentElement.style.setProperty("--accent-color", "#ff6600");
      } else if (value === "2") {
        document.documentElement.style.setProperty("--accent-color", "#cc0000");
      }

      document.documentElement.setAttribute("data-personality", value);
      toggleEl.querySelectorAll("button").forEach(btn => btn.classList.remove("active"));
      e.target.classList.add("active");
    }
  });
  const header = document.getElementById("header-right");
  header.appendChild(toggleEl);

    start();


});
