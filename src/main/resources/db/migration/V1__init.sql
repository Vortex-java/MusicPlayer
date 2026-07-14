create table users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL,
    CONSTRAINT uk_users_login UNIQUE (login),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    CONSTRAINT chk_users_role CHECK ( role IN ('USER', 'ADMIN'))
);

create table artists (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT uk_artists_name UNIQUE (name)
);

create table covers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(75) NOT NULL,
    path VARCHAR(255) NOT NULL
);

create table albums (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(75) NOT NULL,
    artist_id BIGINT NOT NULL,
    release_date DATE NOT NULL,
    cover_id BIGINT,

    CONSTRAINT fk_album_artists FOREIGN KEY (artist_id) REFERENCES artists(id),
    CONSTRAINT fk_album_covers FOREIGN KEY (cover_id) REFERENCES covers(id)
);

create table tracks (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    album_id BIGINT,
    artist_id BIGINT NOT NULL,
    duration BIGINT NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    cover_id BIGINT,

    CONSTRAINT fk_track_albums FOREIGN KEY (album_id) REFERENCES albums(id),
    CONSTRAINT fk_track_artists FOREIGN KEY (artist_id) REFERENCES artists(id),
    CONSTRAINT fk_track_covers FOREIGN KEY (cover_id) REFERENCES covers(id)
);

create table favorite_tracks (
    id BIGSERIAL PRIMARY KEY,
    track_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_fav_tr_tracks FOREIGN KEY (track_id) REFERENCES tracks(id),
    CONSTRAINT fk_fav_tr_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT uk_fav_tr_track_user UNIQUE (track_id, user_id)
);

create table favorite_albums (
    id BIGSERIAL PRIMARY KEY,
    album_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_fav_alb_albums FOREIGN KEY (album_id) REFERENCES albums(id),
    CONSTRAINT fk_fav_alb_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT uk_fav_alb_album_user UNIQUE (album_id, user_id)
);

create table favorite_artists (
    id BIGSERIAL PRIMARY KEY,
    artist_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_fav_art_artists FOREIGN KEY (artist_id) REFERENCES artists(id),
    CONSTRAINT fk_fav_art_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT uk_fav_art_artist_user UNIQUE (artist_id, user_id)
);

create table history (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    track_id BIGINT NOT NULL,
    played_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_history_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_history_tracks FOREIGN KEY (track_id) REFERENCES tracks(id)
);