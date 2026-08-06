import { bindHomeEvents } from "./home/events.js";
import { bindAlbumEvents } from "./album/events.js";
import {bindArtistEvents, bindMoreArtistAlbumsEvents, bindMoreArtistSongsEvents} from "./artist/events.js";
import { state } from "./state.js";
import {bindProfileEvents} from "./profile/events.js";
import {bindLoginEvents} from "./login/events.js";
import {bindRegisterEvents} from "./register/events.js";

const app = document.getElementById("app");
const header = document.getElementById("app-header");
const app_player = document.getElementById("app-player");
const down_player = document.getElementById("down-player");

const routes = {
    home: { auth: true },
    album: { auth: true },
    artist: { auth: true },
    "more-artist-albums": { auth: true },
    "more-artist-songs": { auth: true },
    profile: { auth: true },
    player: { auth: true },
    login: { auth: false },
    register: { auth: false }
};

function isAuthenticated() {
    return localStorage.getItem("accessToken") !== null;
}

async function renderHeader (route) {

    if (route === "home") {
        const response = await fetch("/components/main-header.html");

        const html = await response.text();
        header.innerHTML = html;

        document
            .getElementById("home-button")
            .onclick = () => {

            location.hash = "/";
        };

        document
            .getElementById("global-header-user")
            .onclick = () => {
            window.dispatchEvent(
                new CustomEvent("Navigate", {
                    detail: {
                        route: "profile",
                        params: {
                            pushHistory: true
                        }
                    }
                })
            );
        }
    } else {
        header.innerHTML = "";
    }
}

export async function handleRoute () {

    const hash = location.hash;

    if (hash.startsWith("#/artist/") && hash.endsWith("/albums")) {

        const id = hash.split("/")[2];
        await navigate("more-artist-albums", {
            id,
            pushHistory: false
        })

        return;
    }

    if (hash.startsWith("#/artist/") && hash.endsWith("/songs")) {

        const id = hash.split("/")[2];
        await navigate("more-artist-songs", {
            id,
            pushHistory: false
        })

        return;
    }

    if (hash.startsWith("#/album/")) {

        const id = hash.split("/")[2];

        await navigate("album", {
            id,
            pushHistory: false
        });

        return;
    }

    if (hash.startsWith("#/artist/")) {

        const id = hash.split("/")[2];

        await navigate("artist", {
            id,
            pushHistory: false
        });

        return;
    }

    if (hash.startsWith("#/profile")) {
        const id = hash.split("/")[2];

        await navigate("profile", {
            id,
            pushHistory: false
        });

        return;
    }

    await navigate("home", {
        pushHistory: false
    });
}

window.addEventListener("hashchange", handleRoute);

export async function navigate (route, params = {}) {

    const routeConfig = routes[route];

    if (!routeConfig) {
        console.error(`Unknown route: ${route}`);
        return;
    }

    if (routeConfig.auth && !isAuthenticated()) {
        route = "login";
        console.log("unauthorized");
    }

    app.classList.add("fade-out");
    header.classList.add("fade-out");

    await new Promise(resolve => setTimeout(resolve, 200));

    if (params.pushHistory !== false) {

        if (route === "album") {
            state.currentAlbumId = params.id;
            history.pushState({}, "", `#/album/${params.id}`);
        }

        if (route === "artist") {
            state.currentArtistId = params.id;
            history.pushState({}, "", `#/artist/${params.id}`);
        }

        if (route === "home") {
            window.scrollTo(0, 0);
            history.pushState({}, "", "#/");
        }

        if (route === "more-artist-albums") {
            window.scrollTo(0, 0);
            history.pushState({}, "", `#/artist/${params.id}/albums`);
        }

        if (route === "more-artist-songs") {
            window.scrollTo(0, 0);
            history.pushState({}, "", `#/artist/${params.id}/songs`);
        }

        if (route === "profile") {
            window.scrollTo(0, 0);
            history.pushState({}, "", `#/profile`);
        }
    }

    await renderHeader(route);

    const response = await fetch(`/pages/${route}.html`);

    app.innerHTML = await response.text();

    switch (route) {
        case "home":
            await bindHomeEvents();
            break;

        case "album":
            await bindAlbumEvents(params.id);
            window.scrollTo(0, 0);
            break;

        case "artist":
            await bindArtistEvents(params.id);
            window.scrollTo(0, 0);
            break;

        case "more-artist-albums":
            await bindMoreArtistAlbumsEvents(params.id);
            window.scrollTo(0, 0);
            break;

        case "more-artist-songs":
            await bindMoreArtistSongsEvents(params.id);
            window.scrollTo(0, 0);
            break;

        case "profile":
            bindProfileEvents();
            break;

        case "login":
            bindLoginEvents();
            break;

        case "register":
            bindRegisterEvents();
            break;
    }

    app.classList.remove("fade-out");
    header.classList.remove("fade-out");
}

window.addEventListener(
    "ShowMainHidePlayer",
    async ()=> {
        app.classList.add("fade-out");
        app_player.classList.add("fade-out");
        header.classList.add("fade-out");
        down_player.classList.add("fade-out");

        await new Promise(resolve => setTimeout(resolve, 200));

        app.classList.remove("hidden");
        header.classList.remove("hidden");
        down_player.classList.remove("down-player-hidden-2");

        app_player.classList.add("hidden");

        app.classList.remove("fade-out");
        app_player.classList.remove("fade-out");
        header.classList.remove("fade-out");
        down_player.classList.remove("fade-out");
    }
)

window.addEventListener(
    "ShowPlayerHideMain",
    async () => {
        app.classList.add("fade-out");
        app_player.classList.add("fade-out");
        header.classList.add("fade-out");
        down_player.classList.add("fade-out");

        await new Promise(resolve => setTimeout(resolve, 200));

        app.classList.add("hidden");
        header.classList.add("hidden");
        down_player.classList.add("down-player-hidden-2");

        app_player.classList.remove("hidden");

        app.classList.remove("fade-out");
        app_player.classList.remove("fade-out");
        header.classList.remove("fade-out");
        down_player.classList.remove("fade-out");
    }
)

window.addEventListener(
    "Navigate",
    async (event) => {
        await navigate(
            event.detail.route,
            event.detail.params
        )
    }
)