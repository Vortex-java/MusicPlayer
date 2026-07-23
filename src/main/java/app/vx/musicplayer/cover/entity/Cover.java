package app.vx.musicplayer.cover.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "covers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 75)
    private String name;

    @Column(nullable = false)
    private String path;

    public Cover (String name, String path) {
        this.name = name;
        this.path = path;
    }
}