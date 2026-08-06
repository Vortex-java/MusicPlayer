import {apiFetch} from "../api/apiFetch.js";
import {renderAlbumPageInfo, renderAlbumTracks} from "./render.js";
import {state} from "../state.js";

export async function bindAlbumEvents (albumId) {

    const data = await apiFetch(`/api/albums/${albumId}`);

    await renderAlbumPageInfo(data);

    await renderAlbumTracks(data.tracks);

    document
        .getElementById("album-page-back-button")
        .onclick = () => {
            history.back();
    };

    document
        .getElementById("album-page-profile")
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
    };

    document
        .getElementById("album-page-artist")
        .onclick = (event) => {

        const artistId = event.currentTarget.dataset.artistId;

        window.dispatchEvent(
            new CustomEvent("Navigate", {
                detail: {
                    route: "artist",
                    params: {
                        id: artistId,
                        pushHistory: true
                    }
                }
            })
        );
    }

    document.querySelectorAll(".album-page-track")
        .forEach(card => {
            card.onclick = () => {

                state.queue = data.tracks.map(t => t.id);
                state.currentIndex = state.queue.indexOf(Number(card.dataset.trackId));

                window.dispatchEvent(
                    new CustomEvent("PlayAudio", {
                        detail: {
                            id: Number(card.dataset.trackId)
                        }
                    })
                )
            }
        })
}