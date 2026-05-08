import start from "../js/router.js";
import { toggleButton } from "./views/components/toggle.js";

addEventListener("DOMContentLoaded", () => {

  const toggleEl = toggleButton();
  const header = document.getElementById("header-right");
  header.appendChild(toggleEl);

    start();


});
