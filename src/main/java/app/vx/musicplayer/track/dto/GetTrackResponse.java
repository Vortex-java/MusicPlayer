package app.vx.musicplayer.track.dto;

public record GetTrackResponse(
        Long id,
        String name,
        String coverUrl,
        String artistName,
        Long duration
) {
}
