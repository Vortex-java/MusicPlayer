package app.vx.musicplayer.home.dto;

import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;
import app.vx.musicplayer.artist.dto.GetArtistPreviewResponse;
import app.vx.musicplayer.track.dto.GetTrackResponse;

import java.util.List;

public record GetHomePageResponse(
        List<GetArtistPreviewResponse> artists,
        List<GetPreviewAlbumResponse> albums,
        List<GetTrackResponse> tracks
) {
}
