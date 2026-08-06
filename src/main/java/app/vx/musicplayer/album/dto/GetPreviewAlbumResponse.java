package app.vx.musicplayer.album.dto;

public record GetPreviewAlbumResponse(
        Long id,
        String name,
        String coverUrl
) {
}