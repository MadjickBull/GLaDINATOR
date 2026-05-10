import { RESOURCE_URL } from "../../config.js";

export function render(devs) {
  const app = document.querySelector("#app");
  app.innerHTML = "";

  const aboutWallpaper = document.createElement("video");
  aboutWallpaper.src = RESOURCE_URL + "AboutPage.mp4";
  aboutWallpaper.loop = true;
  aboutWallpaper.autoplay = true;
  aboutWallpaper.muted = true;
  aboutWallpaper.style.position = "absolute";
  aboutWallpaper.style.top = "0";
  aboutWallpaper.style.left = "0";
  aboutWallpaper.style.width = "100%";
  aboutWallpaper.style.height = "100%";
  aboutWallpaper.style.zIndex = "-1";
  aboutWallpaper.style.objectFit = "cover";

  const aboutParagraph = document.createElement("p");
  aboutParagraph.id = "about-paragraph";
  aboutParagraph.textContent =
    "This project is a single-page application inspired by the classic Akinator experience, reimagined through the voice and personality of GLaDOS. It challenges users to think of a character while the system asks a series of adaptive questions to narrow down the answer. Blending playful artificial intelligence with a darkly humorous tone, the app showcases dynamic decision-making, interactive UI design, and a narrative twist that makes each session feel like a test you were never meant to pass.";

  aboutParagraph.classList.add("about-paragraph");

  const devList = document.createElement("ul");
  devList.classList.add("dev-list");

  const content = document.createElement("div");
  content.classList.add("about-content");

  devs.forEach((dev) => {
    const card = document.createElement("div");
    const avatar = document.createElement("div");
    const name = document.createElement("h3");
    const role = document.createElement("p");
    const githubLink = document.createElement("a");
    const githubLogo = document.createElement("img");

    githubLogo.src = RESOURCE_URL + "GithubLogo.png";
    githubLogo.alt = "GitHub Logo";

    name.textContent = dev.name;
    role.textContent = dev.role;
    avatar.textContent = dev.initials;

    githubLink.classList.add("dev-github");
    githubLink.href = dev.github;
    card.classList.add("dev-card");
    avatar.classList.add("dev-avatar");

    card.appendChild(avatar);
    card.appendChild(name);
    card.appendChild(role);
    githubLink.appendChild(githubLogo);
    card.appendChild(githubLink);
    devList.appendChild(card);
  });

  content.appendChild(aboutParagraph);
  content.appendChild(devList);
  app.appendChild(content);
  app.appendChild(aboutWallpaper);

  return app;
}
