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

    @Column(name = "image_url")
    private String imageUrl;

    public Artist (String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public void setName (String name) {
        this.name = name;
    }

    public void setImageUrl (String imageUrl) {
        this.imageUrl = imageUrl;
    }
}