package app.vx.musicplayer.history.dto;

import app.vx.musicplayer.track.dto.GetTrackResponse;

import java.util.List;

public record GetHistoryResponse(

        List<GetTrackResponse> tracks
) {
}
