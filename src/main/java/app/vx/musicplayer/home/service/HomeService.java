package app.vx.musicplayer.home.service;

import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;
import app.vx.musicplayer.album.mapper.AlbumMapper;
import app.vx.musicplayer.artist.mapper.ArtistMapper;
import app.vx.musicplayer.track.mapper.TrackMapper;
import app.vx.musicplayer.artist.dto.GetArtistPreviewResponse;
import app.vx.musicplayer.common.finder.AlbumFinder;
import app.vx.musicplayer.common.finder.ArtistFinder;
import app.vx.musicplayer.common.finder.TrackFinder;
import app.vx.musicplayer.home.dto.GetHomePageResponse;
import app.vx.musicplayer.track.dto.GetTrackResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private final ArtistFinder artistFinder;
    private final AlbumFinder albumFinder;
    private final TrackFinder trackFinder;

    private final ArtistMapper artistMapper;
    private final AlbumMapper albumMapper;
    private final TrackMapper trackMapper;

    public HomeService (
            ArtistFinder artistFinder,
            AlbumFinder albumFinder,
            TrackFinder trackFinder,

            ArtistMapper artistMapper,
            AlbumMapper albumMapper,
            TrackMapper trackMapper
    ) {
        this.artistFinder = artistFinder;
        this.albumFinder = albumFinder;
        this.trackFinder = trackFinder;

        this.artistMapper = artistMapper;
        this.albumMapper = albumMapper;
        this.trackMapper = trackMapper;
    }

    public GetHomePageResponse getHome () {

        List<GetArtistPreviewResponse> artists = artistFinder
                .findTop4ByOrderByIdDesc()
                .stream()
                .map(artistMapper::toPreviewResponse)
                .toList();

        List<GetPreviewAlbumResponse> albums = albumFinder
                .findTop4ByOrderByIdDesc()
                .stream()
                .map(albumMapper::toPreviewResponse)
                .toList();

        List<GetTrackResponse> tracks = trackFinder
                .findTop10ByOrderByIdDesc()
                .stream()
                .map(trackMapper::toResponse)
                .toList();

        return new GetHomePageResponse(
                artists,
                albums,
                tracks
        );

    }
}
