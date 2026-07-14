package app.vx.musicplayer.auth.service;

import app.vx.musicplayer.auth.dto.RegisterRequest;
import app.vx.musicplayer.exception.LoginAlreadyExistsException;
import app.vx.musicplayer.user.entity.Role;
import app.vx.musicplayer.user.entity.User;
import app.vx.musicplayer.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService (UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register (RegisterRequest request) {
        if (userRepository.existsByLogin(request.login())) {
            throw new LoginAlreadyExistsException("Login already exists");
        }

        User newUser = new User(
                request.username(),
                request.login(),
                passwordEncoder.encode(request.password()),
                Role.USER
        );

        userRepository.save(newUser);
    }
}
