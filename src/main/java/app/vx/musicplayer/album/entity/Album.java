package app.vx.musicplayer.album.entity;

import app.vx.musicplayer.artist.entity.Artist;
import app.vx.musicplayer.cover.entity.Cover;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "albums")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 75)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id")
    private Cover cover;

    public Album (String name, Artist artist, LocalDate releaseDate, Cover cover) {
        this.name = name;
        this.artist = artist;
        this.releaseDate = releaseDate;
        this.cover = cover;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setArtist (Artist artist) {
        this.artist = artist;
    }

    public void setReleaseDate (LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setCover (Cover cover) {
        this.cover = cover;
    }
}