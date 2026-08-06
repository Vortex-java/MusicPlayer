package app.vx.musicplayer.track.dto;

public record GetAlbumTrackResponse(
        Long id,
        Integer trackNumber,
        String name,
        String coverUrl,
        String artistName,
        Long duration
) {
}
