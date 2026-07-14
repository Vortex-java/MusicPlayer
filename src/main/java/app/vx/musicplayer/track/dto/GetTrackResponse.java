package app.vx.musicplayer.track.dto;

public record GetTrackResponse(

        String name,
        String coverUrl,
        Long artistName,
        Long albumName,
        Long duration
) {
}
