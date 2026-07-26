package app.vx.musicplayer.common.finder;

import app.vx.musicplayer.cover.entity.Cover;
import org.springframework.stereotype.Component;

@Component
public class CoverUrlFinder {
    public String findUrl (Cover cover) {
        String coverUrl = "/images/default-cover.png";

        if (cover != null) {
            coverUrl = "/api/covers/" + cover.getId() + "/file";
        }

        return coverUrl;
    }
}
