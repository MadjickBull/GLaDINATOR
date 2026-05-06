import { RESOURCE_URL } from "../../config.js";

export function render() {
  const app = document.querySelector("#app");
  app.innerHTML = "";

  const aboutWallpaper = document.createElement("video");
  aboutWallpaper.src = RESOURCE_URL + "AboutPage.mp4";
  aboutWallpaper.loop = true;
  aboutWallpaper.autoplay = true;
  aboutWallpaper.muted = true;

  const aboutParagraph = document.createElement("p");
  aboutParagraph.textContent =
    "This project is a single-page application inspired by the classic Akinator experience, reimagined through the voice and personality of GLaDOS. It challenges users to think of a character while the system asks a series of adaptive questions to narrow down the answer. Blending playful artificial intelligence with a darkly humorous tone, the app showcases dynamic decision-making, interactive UI design, and a narrative twist that makes each session feel like a test you were never meant to pass.";

  const devList = document.createElement("ul");

  const devs = [
    { name: "Filipe", github: "https://github.com/MadjickBull" },
    { name: "Nuno", github: "https://github.com/Nokz22" },
    { name: "Sucena", github: "https://github.com/franciscosucena03" },
    { name: "Tãnia", github: "https://github.com/tsmartinsgoncalves" },
  ];

  devs.forEach((dev) => {
    const li = document.createElement("li");
    const logo = document.createElement("img");
    const name = document.createElement("h5");
    const link = document.createElement("a");

    name.textContent = dev.name;

    logo.src = RESOURCE_URL + "GithubLogo.png";
    logo.style.width = "14px";
    logo.style.height = "14px";

    link.textContent = dev.github;
    link.href = dev.github;

    link.appendChild(logo);

    li.appendChild(name);
    li.appendChild(link);

    devList.appendChild(li);
  });

  app.appendChild(devList);

  app.appendChild(aboutParagraph);
  app.appendChild(aboutWallpaper);

  return app;
}
