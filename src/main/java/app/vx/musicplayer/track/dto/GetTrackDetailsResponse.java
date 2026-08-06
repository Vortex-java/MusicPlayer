package app.vx.musicplayer.track.dto;

public record GetTrackDetailsResponse(
        Long id,
        String name,
        String path,
        String coverUrl,
        String lyricsPath,
        Long artistId,
        String artistName,
        Long duration
) {
}