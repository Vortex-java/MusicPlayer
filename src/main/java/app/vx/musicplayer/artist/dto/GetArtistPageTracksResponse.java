package app.vx.musicplayer.artist.dto;

import app.vx.musicplayer.common.dto.PageResponse;
import app.vx.musicplayer.track.dto.GetTrackResponse;

public record GetArtistPageTracksResponse(
        String name,
        PageResponse<GetTrackResponse> tracks
) {
}
