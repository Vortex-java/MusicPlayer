import {API_URL} from "../api/config.js";
import {trackFormatDuration} from "../api/trackFormatDuration.js";

export async function renderArtistsAlbums (albums) {
    const container = document.getElementById("artist-page-albums")

    container.innerHTML = "";

    albums.forEach(album => {
        container.innerHTML += `
            <a class="main-page-album-card" data-album-id="${album.id}">
                <img class="main-page-album-image" src="${API_URL}${album.coverUrl}"/>
                <h3 class="main-page-album-name">${album.name}</h3>
            </a>
        `;
    })
}

export async function renderArtistTracks (tracks) {
    const container = document.getElementById("artist-page-tracks")

    container.innerHTML = "";

    tracks.forEach(track => {

        const duration = trackFormatDuration(track.duration);

        container.innerHTML += `
            <a class="main-page-track-card" data-track-id="${track.id}" data-track-duration="${duration}">
                <img class="main-page-track-image" src="${API_URL}${track.coverUrl}">

                <div class="main-page-track-info-text">
                    <h4 class="main-page-track-name">
                        ${track.name}
                    </h4>
                    <h4 class="main-page-track-artist">
                        ${track.artistName}
                    </h4>
                    <h4 class="main-page-track-artist">
                        ${duration}
                    </h4>
                </div>
            </a>
        `;
    })
}

export function renderAlbums(albums) {

    const container = document.getElementById("more-artist-albums-container");

    albums.forEach(album => {

        container.insertAdjacentHTML("beforeend", `
            <a class="main-page-album-card"
               data-album-id="${album.id}">

                <img class="main-page-album-image"
                     src="${API_URL}${album.coverUrl}">

                <h3 class="main-page-album-name">
                    ${album.name}
                </h3>
            </a>
        `);

    });
}

export function renderTracks(tracks) {

    const container = document.getElementById("more-artist-songs-container");

    tracks.forEach(track => {

        const duration = trackFormatDuration(track.duration);

        container.insertAdjacentHTML("beforeend", `
            <a class="main-page-track-card"
               data-track-id="${track.id}"
               data-track-duration="${duration}">

                <img class="main-page-track-image"
                     src="${API_URL}${track.coverUrl}">

                <div class="main-page-track-info-text">

                    <h4 class="main-page-track-name">
                        ${track.name}
                    </h4>

                    <h4 class="main-page-track-artist">
                        ${track.artistName}
                    </h4>

                    <h4 class="main-page-track-artist">
                        ${duration}
                    </h4>

                </div>

            </a>
        `);

    });

    container.querySelectorAll(".main-page-track-card").forEach(card => {

        card.onclick = () => {

            window.dispatchEvent(
                new CustomEvent("PlayAudio", {
                    detail: {
                        id: card.dataset.trackId,
                        duration: card.dataset.trackDuration
                    }
                })
            );

        };

    });
}