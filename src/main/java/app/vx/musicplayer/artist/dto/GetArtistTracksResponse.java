package app.vx.musicplayer.artist.dto;

import app.vx.musicplayer.track.dto.GetTrackResponse;

import java.util.List;

public record GetArtistTracksResponse(

        List<GetTrackResponse> tracks
) {
}
