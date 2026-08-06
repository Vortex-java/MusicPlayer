package app.vx.musicplayer.common.finder;

import app.vx.musicplayer.artist.entity.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistImageUrlFinder {

    public String findImageUrl (Artist artist) {
        String imageUrl = "/images/default-artist.png";

        if (artist.getImageUrl() != null) {
            imageUrl = "/api/artists/" + artist.getId() + "/file";
        }

        return imageUrl;
    }
}
