package app.vx.musicplayer.track.dto;

public record GetTrackResponse(

        String name,
        String coverUrl,
        String artistName,
        String albumName,
        Long duration
) {
}
