package app.vx.musicplayer.album.dto;

import java.time.LocalDate;

public record GetPreviewAlbumResponse(

        Long id,
        String name,
        Long artistId,
        String artistName,
        String coverUrl,
        LocalDate releaseDate
) {
}