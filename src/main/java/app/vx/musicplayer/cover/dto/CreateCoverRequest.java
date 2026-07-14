package app.vx.musicplayer.cover.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record CreateCoverRequest(

        @NotNull(message = "file is empty")
        MultipartFile file,

        @NotBlank(message = "cover name is empty")
        @Size(max = 75)
        String name
) {
}
