package app.vx.musicplayer.album.mapper;

import app.vx.musicplayer.common.finder.CoverUrlFinder;
import app.vx.musicplayer.track.dto.GetTrackDetailsResponse;
import app.vx.musicplayer.track.dto.GetTrackResponse;
import app.vx.musicplayer.track.entity.Track;
import org.springframework.stereotype.Component;

@Component
public class TrackMapper {

    private final CoverUrlFinder coverUrlFinder;

    public TrackMapper (CoverUrlFinder coverUrlFinder) {
        this.coverUrlFinder = coverUrlFinder;
    }

    public GetTrackResponse toResponse (Track track) {
        return new GetTrackResponse(
                track.getName(),
                coverUrlFinder.findUrl(track.getCover()),
                track.getArtist().getName(),
                track.getDuration()
        );
    }

    public GetTrackDetailsResponse toDetails (Track track) {
        return new GetTrackDetailsResponse(
                track.getName(),
                coverUrlFinder.findUrl(track.getCover()),
                track.getArtist().getId(),
                track.getArtist().getName(),
                track.getAlbum().getId(),
                track.getAlbum().getName(),
                track.getDuration()
        );
    }
}
