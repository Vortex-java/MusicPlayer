import {API_URL} from "../api/config.js";
import {trackFormatDuration} from "../api/trackFormatDuration.js";

export async function renderAlbumPageInfo (album) {

    document.getElementById("album-page-info-container").innerHTML =
        `
        <a id="album-page-artist" class="album-page-album-artist" data-artist-id="${album.artistId}">
                <h1 class="album-page-artist-h1">
                    ${album.artistName}
                </h1>
            </a>
    `;

    document.getElementById("album-page-info").innerHTML =
        `
        <img class="album-page-album-image" src="${API_URL}${album.coverUrl}">

        <div class="album-page-album-text">

            <h3 class="album-page-album-name">
                ${album.name}
            </h3>

            <h3 class="album-page-album-release">
                ${album.releaseDate}
            </h3>
        </div>
        `;

}

export async function renderAlbumTracks (tracks) {
    const container = document.getElementById("album-page-tracks");

    container.innerHTML = "";

    tracks.forEach(track => {

        const duration = trackFormatDuration(track.duration);

        container.innerHTML +=
            `
            <a class="album-page-track" data-track-id="${track.id}" data-track-duration="${duration}">

                <div class="album-page-track-img">
                    <img class="album-page-track-image" src="${API_URL}${track.coverUrl}">
                </div>

                <div class="album-page-track-text">
                    <h4 class="album-page-track-name">
                        ${track.artistName}
                    </h4>
                    <h4 class="album-page-track-artist">
                        ${track.name}
                    </h4>
                </div>

                <div class="album-page-track-duration">
                    <h4>
                        ${duration}
                    </h4>
                </div>
            </a>
            `
    });
}