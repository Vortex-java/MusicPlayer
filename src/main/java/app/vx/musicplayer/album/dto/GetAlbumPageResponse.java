package app.vx.musicplayer.album.dto;

import app.vx.musicplayer.track.dto.GetAlbumTrackResponse;

import java.time.LocalDate;
import java.util.List;

public record GetAlbumPageResponse(
        String name,
        Long artistId,
        String artistName,
        LocalDate releaseDate,
        String coverUrl,
        List<GetAlbumTrackResponse> tracks
) {
}