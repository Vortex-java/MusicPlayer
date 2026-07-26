package app.vx.musicplayer.track.dto;

public record GetTrackDetailsResponse(
        String name,
        String coverUrl,
        Long artistId,
        String artistName,
        Long albumId,
        String albumName,
        Long duration
) {
}
