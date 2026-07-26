package app.vx.musicplayer.album.mapper;

import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;
import app.vx.musicplayer.album.entity.Album;
import app.vx.musicplayer.common.finder.CoverUrlFinder;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {

    private final CoverUrlFinder coverUrlFinder;

    public AlbumMapper (CoverUrlFinder coverUrlFinder) {
        this.coverUrlFinder = coverUrlFinder;
    }

    public GetPreviewAlbumResponse toPreviewResponse (Album album) {

        return new GetPreviewAlbumResponse(
                album.getId(),
                album.getName(),
                album.getArtist().getId(),
                album.getArtist().getName(),
                coverUrlFinder.findUrl(album.getCover()),
                album.getReleaseDate()
        );
    }
}
