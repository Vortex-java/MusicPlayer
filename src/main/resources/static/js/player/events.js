import {player} from "./audioManager.js";
import {loadLyrics, noneLyrics, updateLyrics} from "./lyrics.js";
import {state} from "../state.js";
import {apiFetch} from "../api/apiFetch.js";
import {API_URL} from "../api/config.js";
import {trackFormatDuration} from "../api/trackFormatDuration.js";

let track = null;

let playButtonPlayer;
let playButtonDownPlayer;
let playIcon;
let playIconDown;

let closeDownPlayer;

let downPlayerZone;

let nextButton;
let prevButton;

export function bindMusicPlayerEvents () {
    document
        .getElementById("player-page-back-button")
        .onclick = () => {
            window.dispatchEvent(
                new CustomEvent("ShowMainHidePlayer")
            );
    };

    playButtonPlayer = document.querySelector(".play");
    playButtonDownPlayer = document.querySelector(".down-player-btn");

    playIcon = playButtonPlayer.querySelector("img");
    playIconDown = playButtonDownPlayer.querySelector("img");

    downPlayerZone = document.querySelector(".down-player");

    closeDownPlayer = document.getElementById("close-down-player");

    nextButton = document.querySelector(".next");
    prevButton = document.querySelector(".prev");

    nextButton.onclick = () => {
        playNext();
    };

    prevButton.onclick = () => {
        playPrev();
    };

    playButtonPlayer.onclick = () => {
        if (player.paused) {
            player.play();
            dispatchEvent(new CustomEvent("ChangePlayIcon"));
        } else {
            player.pause();
            dispatchEvent(new CustomEvent("ChangePauseIcon"));
        }
    }

    playButtonDownPlayer.onclick = (e) => {

        e.stopPropagation();

        if (player.paused) {
            player.play();
            window.dispatchEvent(new CustomEvent("ChangePlayIcon"));
        } else {
            player.pause();
            window.dispatchEvent(new CustomEvent("ChangePauseIcon"));
        }
    }

    downPlayerZone.onclick = () => {
        window.dispatchEvent(
            new CustomEvent("ShowPlayerHideMain")
        );
    }

    closeDownPlayer.onclick = (e) => {
        e.stopPropagation();
        player.pause();
        downPlayerZone.classList.add("down-player-hidden");
        state.currentSongId = null;
        state.queue = [];
        state.currentIndex = null;
    }

    track = document.querySelector(".player-page-progress-track");

    track.addEventListener("pointerdown", (e) => {
        dragging = true;
        seek(e);
    });

    document.addEventListener("pointermove", (e) => {
        if (!dragging) return;

        seek(e);
    });

    document.addEventListener("pointerup", () => {
        dragging = false;
    });

    player.addEventListener("timeupdate", updateLyrics);
}

function playPrev () {
    if (state.queue.length === 0) return;

    state.currentIndex--;

    if (state.currentIndex <= 0) {
        state.currentIndex = state.queue.length - 1;
    }

    window.dispatchEvent(
        new CustomEvent("PlayAudio", {
            detail:{
                id: state.queue[state.currentIndex]
            }
        })
    );
}

function playNext() {

    if (state.queue.length === 0) return;

    state.currentIndex++;

    if (state.currentIndex >= state.queue.length) {
        state.currentIndex = 0;
    }

    window.dispatchEvent(
        new CustomEvent("PlayAudio", {
            detail:{
                id: state.queue[state.currentIndex]
            }
        })
    );
}

function updateSlider() {

    const fill = document.querySelector(".player-page-progress-fill");
    const current = document.querySelector(".player-page-h4-left");

    if (player.duration) {
        const progress = player.currentTime / player.duration * 100;

        fill.style.width = progress + "%";
        current.textContent = formatTime(player.currentTime);
    }

    requestAnimationFrame(updateSlider);
}

updateSlider();

let dragging = false;

function seek(e) {
    const rect = track.getBoundingClientRect();

    let percent = (e.clientX - rect.left) / rect.width;

    percent = Math.max(0, Math.min(1, percent));

    player.currentTime = percent * player.duration;
}

function formatTime(seconds) {
    const min = Math.floor(seconds / 60);
    const sec = Math.floor(seconds % 60);

    return `${min}:${sec.toString().padStart(2, "0")}`;
}

window.addEventListener(
    "ChangePlayIcon",
    async () => {
        playIcon.src = "/images/pause.svg";
        playIconDown.src = "/images/pause.svg";
    }
);

window.addEventListener(
    "ChangePauseIcon",
    async () => {
        playIcon.src = "/images/play.svg";
        playIconDown.src = "/images/play.svg";
    }
)

player.addEventListener("ended", () => {
    //window.dispatchEvent(new CustomEvent("PlayerStop"));
    playNext();
});

window.addEventListener("PlayerStop", () => {
    playIcon.src = "/images/play.svg";
    playIconDown.src = "/images/play.svg";
    player.currentTime = 0;
});

window.addEventListener("PlayAudio",
    async (event) => {
    const id = event.detail.id;

    if (downPlayerZone.classList.contains("down-player-hidden")) {
        downPlayerZone.classList.remove("down-player-hidden");
    }

    if (state.currentSongId !== id) {

        const track = await apiFetch(`/api/tracks/${id}`);

        player.src = `${API_URL}/api/tracks/${track.id}/stream`;

        if (track.lyricsPath) {
            await loadLyrics(`${API_URL}/api/tracks/${track.id}/lyrics`);
        } else {
            await noneLyrics();
        }

        const downPlayer = document.getElementById("down-player-info");

        const trackName = track.name;
        const artistName = track.artistName;
        const coverUrl = track.coverUrl;
        const duration = track.duration;

        const total = document.querySelector(".player-page-h4-right");

        total.textContent = trackFormatDuration(duration);

        downPlayer.innerHTML = "";
        downPlayer.innerHTML += `<div class="down-player-track-name">${trackName}</div>`;
        downPlayer.innerHTML += `<div class="down-player-track-artist">${artistName}</div>`;

        document.getElementById("down-player-cover").src = `${API_URL}${coverUrl}`;

        document.getElementById("player-page-container").innerHTML = `
            <img class="player-page-track-image" src="${API_URL}${coverUrl}">
            <h2 class="player-page-track-info-name">
                ${trackName}
            </h2>
            <h2 class="player-page-track-info-artist">
                ${artistName}
            </h2>
            `

        player.play();
        window.dispatchEvent(new CustomEvent("ChangePlayIcon"));
        state.currentSongId = id;
        return;
    }

    if (state.currentSongId === id) {
        if (player.paused) {
            player.play();
            window.dispatchEvent(new CustomEvent("ChangePlayIcon"));
        } else {
            player.pause();
            window.dispatchEvent(new CustomEvent("ChangePauseIcon"));
        }
    }
});