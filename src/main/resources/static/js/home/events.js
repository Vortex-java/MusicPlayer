import {navigate} from "../router.js";
import {getHomePage} from "../api/home.js";
import {renderAlbums, renderArtists, renderTracks} from "./render.js";
import {state} from "../state.js";

export async function bindHomeEvents() {

    const data = await getHomePage();

    renderArtists(data.artists);

    renderAlbums(data.albums);

    renderTracks(data.tracks);

    document.querySelectorAll(".main-page-album-card")
        .forEach(card => {
            card.onclick = () => {
                navigate("album", {
                    id: card.dataset.albumId,
                    pushHistory: true
                })
            }
        })

    document.querySelectorAll(".main-page-artist-card")
        .forEach(card => {
            card.onclick = () => {
                navigate("artist", {
                    id: card.dataset.artistId,
                    pushHistory: true
                })
            }
        })

    document.querySelectorAll(".main-page-track-card")
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