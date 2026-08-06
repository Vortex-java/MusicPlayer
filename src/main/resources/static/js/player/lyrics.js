import {player} from "./audioManager.js";

let lyrics = [];

export async function loadLyrics(path) {

    const response = await fetch(path);
    const text = await response.text();

    const container = document.querySelector(".player-page-track-text");

    container.innerHTML = "";

    lyrics = [];

    const lines = text.split("\n");

    for (const line of lines) {

        const match = line.match(/\[(\d+):(\d+):(\d+)\](.*)/);

        if (!match) continue;

        const hours = Number(match[1]);
        const minutes = Number(match[2]);
        const seconds = Number(match[3]);


        const time =
            hours * 3600 +
            minutes * 60 +
            seconds;


        const div = document.createElement("div");

        div.textContent = match[4].trim();

        container.appendChild(div);

        lyrics.push({
            time,
            element: div
        });
    }

    if (lyrics.length === 0) {
        const div = document.createElement("div");

        div.textContent = "текст отсутствует";

        container.appendChild(div);
    }
}

export async function noneLyrics () {
    const container = document.querySelector(".player-page-track-text");

    container.innerHTML = "";

    const div = document.createElement("div");

    div.textContent = "текст отсутствует";

    container.appendChild(div);
}

export function updateLyrics() {

    if (lyrics.length !== 0) {
        const currentTime = player.currentTime;

        let currentIndex = 0;


        for (let i = 0; i < lyrics.length; i++) {

            if (currentTime >= lyrics[i].time) {
                currentIndex = i;
            }
        }


        lyrics.forEach((line, index) => {

            line.element.classList.remove(
                "active",
                "previous",
                "next"
            );


            if (index === currentIndex) {

                line.element.classList.add("active");

            } else if (index < currentIndex) {

                line.element.classList.add("previous");

            } else {

                line.element.classList.add("next");

            }

        });

        const active = lyrics[currentIndex];

        if (active) {
            active.element.scrollIntoView({
                behavior: "smooth",
                block: "center"
            });
        }

    }
}