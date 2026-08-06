package app.vx.musicplayer.track.mapper;

import app.vx.musicplayer.common.finder.CoverUrlFinder;
import app.vx.musicplayer.track.dto.GetAlbumTrackResponse;
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
                track.getId(),
                track.getName(),
                coverUrlFinder.findUrl(track.getCover()),
                track.getArtist().getName(),
                track.getDuration()
        );
    }

    public GetAlbumTrackResponse toAlbumTrackResponse (Track track) {
        return new GetAlbumTrackResponse(
                track.getId(),
                track.getTrackNumber(),
                track.getName(),
                coverUrlFinder.findUrl(track.getCover()),
                track.getArtist().getName(),
                track.getDuration()
        );
    }

    public GetTrackDetailsResponse toDetails (Track track) {
        return new GetTrackDetailsResponse(
                track.getId(),
                track.getName(),
                track.getFilePath(),
                coverUrlFinder.findUrl(track.getCover()),
                track.getLyricsPath(),
                track.getArtist().getId(),
                track.getArtist().getName(),
                track.getDuration()
        );
    }
}
