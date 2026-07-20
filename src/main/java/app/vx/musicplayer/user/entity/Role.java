package app.vx.musicplayer.user.entity;

public enum Role {
    USER,
    ADMIN;

    public String getAuthority () {
        return "ROLE_" + name();
    }
}
