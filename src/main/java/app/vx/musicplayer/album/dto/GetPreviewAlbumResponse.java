package app.vx.musicplayer.album.dto;

import java.time.LocalDate;

public record GetPreviewAlbumResponse(

        String name,
        String artistName,
        LocalDate releaseDate,
        String coverUrl
) {
}
