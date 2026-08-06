package app.vx.musicplayer.artist.dto;

import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;
import app.vx.musicplayer.track.dto.GetTrackResponse;

import java.util.List;

public record GetArtistPageResponse(
        String name,
        List<GetTrackResponse> latestTracks,
        List<GetPreviewAlbumResponse> latestAlbums
) {
}
