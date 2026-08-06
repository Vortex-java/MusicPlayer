import {state} from "../state.js";
import {apiFetch} from "../api/apiFetch.js";
import {renderAlbums, renderArtistsAlbums, renderArtistTracks, renderTracks} from "./render.js";

export async function bindArtistEvents (artistId) {

    state.currentArtistId = artistId;

    const data = await apiFetch(`/api/artists/${artistId}`);

    document.getElementById('artist-page-artist-name').innerHTML = data.name;

    document.getElementById('artist-page-artist-name')
        .setAttribute("data-artist-name", `${data.name}`);

    await renderArtistsAlbums(data.latestAlbums);

    await renderArtistTracks(data.latestTracks);

    document
        .getElementById("album-page-back-button")
        .onclick = () => {
            history.back();
    };

    document
        .getElementById("artist-page-profile")
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

    document.querySelectorAll(".main-page-album-card")
        .forEach(card => {
            card.onclick = () => {

                window.dispatchEvent(
                    new CustomEvent("Navigate", {
                        detail: {
                            route: "album",
                            params: {
                                id: card.dataset.albumId,
                                pushHistory: true
                            }
                        }
                    })
                );
            }
        })

    document
        .getElementById("artist-page-more-albums")
        .onclick = () => {

            window.dispatchEvent(
                new CustomEvent("Navigate", {
                    detail: {
                        route: "more-artist-albums",
                        params: {
                            id: state.currentArtistId,
                            pushHistory: true
                        }
                    }
                })
            );
    }

    document
        .getElementById("artist-page-more-songs")
        .onclick = () => {

            window.dispatchEvent(
                new CustomEvent("Navigate", {
                    detail: {
                        route: "more-artist-songs",
                        params: {
                            id: state.currentArtistId,
                            pushHistory: true
                        }
                    }
                })
            );
    }

    document.querySelectorAll(".main-page-track-card")
        .forEach(card => {
            card.onclick = () => {

                state.queue = data.latestTracks.map(t => t.id);
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
const artistAlbumsState = {
    page: 0,
    loading: false,
    hasNext: true,
    artistId: null
};

async function loadAlbums() {

    if (artistAlbumsState.loading || !artistAlbumsState.hasNext) {
        return;
    }

    artistAlbumsState.loading = true;

    const data = await apiFetch(
        `/api/artists/${artistAlbumsState.artistId}/albums?page=${artistAlbumsState.page}`
    );

    document
        .getElementById("more-artist-albums-artist-name")
        .innerHTML = `${data.name}`;

    renderAlbums(data.albums.content);

    artistAlbumsState.page++;
    artistAlbumsState.hasNext = data.albums.hasNext;

    artistAlbumsState.loading = false;
}

export async function bindMoreArtistAlbumsEvents (id) {
    document
        .getElementById("album-page-back-button")
        .onclick = () => {
        history.back();
    };

    window.addEventListener("scroll", () => {

        if (
            window.innerHeight + window.scrollY >=
            document.body.offsetHeight - 300
        ) {
            loadAlbums();
        }

    });

    const container = document.getElementById("more-artist-albums-container");

    container.innerHTML = "";

    artistAlbumsState.artistId = id;
    artistAlbumsState.page = 0;
    artistAlbumsState.hasNext = true;

    await loadAlbums();

    document
        .getElementById("more-artist-albums-profile")
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

    document.querySelectorAll(".main-page-album-card")
        .forEach(card => {
            card.onclick = () => {

                window.dispatchEvent(
                    new CustomEvent("Navigate", {
                        detail: {
                            route: "album",
                            params: {
                                id: card.dataset.albumId,
                                pushHistory: true
                            }
                        }
                    })
                );
            }
        })
}

const tracksState = {
    page: 0,
    loading: false,
    hasNext: true,
    artistId: null
};

async function loadTracks() {

    if (tracksState.loading || !tracksState.hasNext) {
        return;
    }

    tracksState.loading = true;

    const data = await apiFetch(
        `/api/artists/${tracksState.artistId}/tracks?page=${tracksState.page}`
    );

    document
        .getElementById("more-artist-songs-artist-name")
        .innerHTML = `${data.name}`;

    renderTracks(data.tracks.content);

    tracksState.page++;
    tracksState.hasNext = data.tracks.hasNext;

    tracksState.loading = false;

    return data.tracks.content;
}

export async function bindMoreArtistSongsEvents (id) {
    document
        .getElementById("album-page-back-button")
        .onclick = () => {
            history.back();
    };

    window.addEventListener("scroll", () => {

        if (
            window.innerHeight + window.scrollY >=
            document.body.offsetHeight - 300
        ) {
            loadTracks();
        }

    });

    const container = document.getElementById("more-artist-songs-container");
    container.innerHTML = "";

    tracksState.artistId = id;
    tracksState.page = 0;
    tracksState.hasNext = true;

    let tracks = await loadTracks();

    document
        .getElementById("more-artist-songs-profile")
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

    document.querySelectorAll(".main-page-track-card")
        .forEach(card => {
            card.onclick = () => {

                state.queue = tracks.map(t => t.id);
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