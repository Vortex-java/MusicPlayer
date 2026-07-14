package app.vx.musicplayer.album.dto;

import app.vx.musicplayer.track.dto.GetTrackResponse;

import java.util.List;

public record GetAlbumTracksResponse(

        List<GetTrackResponse> tracks
) {
}
