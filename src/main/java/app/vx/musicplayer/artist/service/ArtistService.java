package app.vx.musicplayer.artist.service;

import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;
import app.vx.musicplayer.album.entity.Album;
import app.vx.musicplayer.album.mapper.AlbumMapper;
import app.vx.musicplayer.album.mapper.TrackMapper;
import app.vx.musicplayer.artist.dto.*;
import app.vx.musicplayer.artist.entity.Artist;
import app.vx.musicplayer.artist.repository.ArtistRepository;
import app.vx.musicplayer.common.dto.PageResponse;
import app.vx.musicplayer.common.finder.AlbumFinder;
import app.vx.musicplayer.common.finder.ArtistFinder;
import app.vx.musicplayer.common.finder.TrackFinder;
import app.vx.musicplayer.exception.ArtistAlreadyExistsException;
import app.vx.musicplayer.track.dto.GetTrackResponse;
import app.vx.musicplayer.track.entity.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistFinder artistfinder;
    private final AlbumFinder albumFinder;
    private final TrackFinder trackFinder;

    private final AlbumMapper albumMapper;
    private final TrackMapper trackMapper;

    public ArtistService (
            ArtistRepository artistRepository,
            ArtistFinder artistfinder,
            AlbumFinder albumFinder,
            AlbumMapper albumMapper,
            TrackFinder trackFinder,
            TrackMapper trackMapper) {
        this.artistRepository = artistRepository;
        this.artistfinder = artistfinder;
        this.albumFinder = albumFinder;
        this.albumMapper = albumMapper;
        this.trackFinder = trackFinder;
        this.trackMapper = trackMapper;
    }

    public void create (CreateArtistRequest request) {
        if (artistRepository.existsByName(request.name())) {
            throw new ArtistAlreadyExistsException("Artist already exists");
        }

        Artist newArtist = new Artist(request.name());

        artistRepository.save(newArtist);
    }

    @Transactional
    public void change (Long id, ChangeArtistRequest request) {

        Artist artist = artistfinder.findByIdOrElseThrow(id);
        Artist artist1 = artistRepository.findByName(request.name());

        if (artist1 != null && !Objects.equals(artist.getId(), artist1.getId())) {
            throw new ArtistAlreadyExistsException("Artist already exists");
        }

        artist.setName(request.name());
    }

    public GetArtistPageResponse getArtist (Long id) {

        Artist artist = artistfinder.findByIdOrElseThrow(id);

        List<GetTrackResponse> popularTracks = trackFinder
                .findLatestByArtistId(id, 10)
                .stream()
                .map(trackMapper::toResponse)
                .toList();

        List<GetPreviewAlbumResponse> albums = albumFinder
                .findLatestByArtistId(id, 5)
                .stream()
                .map(albumMapper::toPreviewResponse)
                .toList();

        return new GetArtistPageResponse(
                artist.getName(),
                popularTracks,
                albums
        );
    }

    public PageResponse<GetPreviewAlbumResponse> getAlbums (Long id, Pageable pageable) {
        artistfinder.checkExists(id);

        Page<Album> albums = albumFinder.findByArtistId(id, pageable);

        return PageResponse.from(
                albums.map(
                        albumMapper::toPreviewResponse
                )
        );
    }

    public PageResponse<GetTrackResponse> getTracks (Long id, Pageable pageable) {
        artistfinder.checkExists(id);

        Page<Track> tracks = trackFinder.findByArtistId(id, pageable);

        return PageResponse.from(
                tracks.map(
                        trackMapper::toResponse
                )
        );
    }
}
