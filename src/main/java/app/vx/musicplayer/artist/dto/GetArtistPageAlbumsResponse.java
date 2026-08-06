package app.vx.musicplayer.artist.dto;

import app.vx.musicplayer.album.dto.GetPreviewAlbumResponse;
import app.vx.musicplayer.common.dto.PageResponse;

public record GetArtistPageAlbumsResponse(
        String name,
        PageResponse<GetPreviewAlbumResponse> albums
) {
}
