package app.vx.musicplayer.artist.dto;

public record GetArtistPreviewResponse(
        Long id,
        String name,
        String imageUrl
) {
}
