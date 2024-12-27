package ru.frenzybe.server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.frenzybe.server.dto.user.JwtAuthenticationResponse;
import ru.frenzybe.server.dto.user.SignInRequest;
import ru.frenzybe.server.dto.user.SignUpRequest;
import ru.frenzybe.server.entities.user.User;
import ru.frenzybe.server.entities.user.UserRole;
import ru.frenzybe.server.exceptions.CustomException;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(@Validated SignUpRequest request) {

        // Проверка на существование пользователя
        if (userService.userExists(request.getUsername())) {
            throw new CustomException("Пользователь с таким именем уже существует", HttpStatus.CONFLICT);
        }

        if (userService.emailExists(request.getEmail())) {
            throw new CustomException("Пользователь с таким email уже существует", HttpStatus.CONFLICT);
        }

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.ROLE_USER)
                .balance(0)
                .build();

        try {
            userService.create(user);
        } catch (Exception e) {
            throw new CustomException("Ошибка при создании пользователя", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(@Validated SignInRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new CustomException("Неверное имя пользователя или пароль", HttpStatus.UNAUTHORIZED);
        } catch (DisabledException e) {
            throw new CustomException("Пользователь отключен", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            throw new CustomException("Ошибка аутентификации", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        var user = userService.userDetailsService().loadUserByUsername(request.getUsername());
        if (user == null) {
            throw new CustomException("Пользователь не найден", HttpStatus.NOT_FOUND);
        }

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
