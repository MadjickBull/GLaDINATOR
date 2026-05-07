import { render } from "../views/pages/about.js";
import { devs } from "../models/devs.js";

export function init() {
  render(devs);
}
