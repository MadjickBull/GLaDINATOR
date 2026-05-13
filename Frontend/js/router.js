import routes from "../js/routes.js";

export default function start() {
  const path = window.location.pathname;
  navigate(path, true);
  setAnchorEventListener();
  addEventListener("popstate", handlePopState);
}

export function navigate(path, firstLoad) {
  if (path === routes.currentPath.path) {
    return;
  }

  const routeKey = Object.keys(routes).find((key) => routes[key].path === path);

  const route = routes[routeKey] || routes.home;

  setCurrentRoute(route);

  firstLoad
    ? history.replaceState(route, "", route.path)
    : history.pushState(route, "", route.path);

  initializeController(route.controller);
}

function setCurrentRoute(route) {
  routes.currentPath.path = route.path;
  routes.currentPath.controller = route.controller;
  updateActiveNavLink(route.path);
}

function updateActiveNavLink(path) {
  document.querySelectorAll("nav a.nav-link").forEach((anchor) => {
    anchor.classList.toggle("active", anchor.pathname === path);
  });
}

async function initializeController(controller) {
  const controllerModule = await import(`./controllers/${controller}.js`);

  controllerModule.init();
}

function setAnchorEventListener() {
  const anchors = document.querySelectorAll("nav a");
  anchors.forEach((anchor) => {
    anchor.addEventListener("click", (event) => {
      event.preventDefault();
      navigate(anchor.pathname);
    });
  });
}

function handlePopState(event) {
  const { state } = event;

  console.log(state);
  const route = state || routes.home;

  setCurrentRoute(route);
  initializeController(route.controller);
}
