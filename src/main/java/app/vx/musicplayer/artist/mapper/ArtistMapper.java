package app.vx.musicplayer.artist.mapper;

import app.vx.musicplayer.artist.dto.GetArtistPreviewResponse;
import app.vx.musicplayer.artist.entity.Artist;
import app.vx.musicplayer.common.finder.ArtistImageUrlFinder;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    private final ArtistImageUrlFinder artistImageUrlFinder;

    public ArtistMapper (ArtistImageUrlFinder artistImageUrlFinder) {
        this.artistImageUrlFinder = artistImageUrlFinder;
    }

    public GetArtistPreviewResponse toPreviewResponse (Artist artist) {
        return new GetArtistPreviewResponse(
                artist.getId(),
                artist.getName(),
                artistImageUrlFinder.findImageUrl(artist)
        );
    }
}
