package app.vx.musicplayer.auth.dto;

import app.vx.musicplayer.user.entity.Role;

public record LoginResponse(

        String accessToken,
        String username,

        Role role
) {
}
