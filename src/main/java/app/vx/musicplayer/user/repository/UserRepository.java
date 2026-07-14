package app.vx.musicplayer.user.repository;

import app.vx.musicplayer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    boolean existsByLogin(String login);
}
