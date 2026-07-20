package app.vx.musicplayer.auth.service;

import app.vx.musicplayer.auth.dto.LoginRequest;
import app.vx.musicplayer.auth.dto.LoginResponse;
import app.vx.musicplayer.auth.dto.RegisterRequest;
import app.vx.musicplayer.exception.InvalidCredentialsException;
import app.vx.musicplayer.exception.LoginAlreadyExistsException;
import app.vx.musicplayer.exception.UserNotFoundException;
import app.vx.musicplayer.security.JwtService;
import app.vx.musicplayer.user.entity.Role;
import app.vx.musicplayer.user.entity.User;
import app.vx.musicplayer.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService (UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

    public LoginResponse login (LoginRequest request) {
        User user = userRepository.findByLogin(request.login());

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        boolean matchers = passwordEncoder.matches(
                request.password(),
                user.getPassword()
        );

        if (!matchers) {
            throw new InvalidCredentialsException("Invalid login or password");
        }

        String token = jwtService.generateToken(request.login());
        return new LoginResponse(
                token,
                user.getUsername(),
                user.getRole()
        );
    }
}
