package app.vx.musicplayer.artist.dto;

import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;

import java.util.List;

public record GetArtistAlbumsResponse(

        List<GetPreviewAlbumResponse> albums
) {
}
