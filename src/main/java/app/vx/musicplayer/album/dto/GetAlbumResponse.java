package app.vx.musicplayer.album.dto;

import app.vx.musicplayer.track.dto.GetTrackResponse;

import java.time.LocalDate;
import java.util.List;

public record GetAlbumResponse(

        Long id,
        String name,
        Long artistId,
        String artistName,
        LocalDate releaseDate,
        String coverUrl,
        List<GetTrackResponse> tracks
) {
}