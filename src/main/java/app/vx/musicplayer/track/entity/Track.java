package app.vx.musicplayer.track.entity;

import app.vx.musicplayer.album.entity.Album;
import app.vx.musicplayer.artist.entity.Artist;
import app.vx.musicplayer.cover.entity.Cover;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tracks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(nullable = false)
    private Long duration;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id")
    private Cover cover;

    public Track (String name, Album album, Artist artist, Long duration, String filePath, Cover cover) {
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.filePath = filePath;
        this.cover = cover;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}