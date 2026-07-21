package app.vx.musicplayer.artist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "artists")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    public Artist (String name) {
        this.name = name;
    }

    public void setName (String name) {
        this.name = name;
    }
}