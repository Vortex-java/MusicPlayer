package app.vx.musicplayer.album.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateAlbumRequest(

        @NotBlank(message = "album name is empty")
        @Size(max = 75)
        String name,

        @NotNull(message = "artist id is empty")
        Long artistId,

        @NotNull(message = "release date is empty")
        LocalDate releaseDate,

        Long coverId
) {
}
