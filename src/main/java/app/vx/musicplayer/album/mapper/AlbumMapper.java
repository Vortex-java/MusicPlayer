package app.vx.musicplayer.album.mapper;

import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;
import app.vx.musicplayer.album.entity.Album;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {
    public GetPreviewAlbumResponse toPreviewResponse (Album album) {

        String coverUrl = "/images/default-cover.png";

        if (album.getCover() != null) {
            coverUrl = "/api/covers/" + album.getCover().getId() + "/file";
        }

        return new GetPreviewAlbumResponse(
                album.getId(),
                album.getName(),
                album.getArtist().getId(),
                album.getArtist().getName(),
                coverUrl,
                album.getReleaseDate()
        );
    }
}
