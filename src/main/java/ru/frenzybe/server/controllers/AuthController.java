package ru.frenzybe.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.frenzybe.server.dto.user.SignInRequest;
import ru.frenzybe.server.dto.user.SignUpRequest;
import ru.frenzybe.server.dto.user.TokenValidDTO;
import ru.frenzybe.server.exceptions.CustomException;
import ru.frenzybe.server.services.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<?> signIn(@Validated @RequestBody SignInRequest request) {
        try {
            return new ResponseEntity<>(authenticationService.signIn(request), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Произошла ошибка при обработке запроса", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Validated @RequestBody SignUpRequest request) {
        try {
            return new ResponseEntity<>(authenticationService.signUp(request), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Произошла ошибка при обработке запроса", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate_token")
    public ResponseEntity<?> validateToken(@RequestBody TokenValidDTO tokenValidDTO) {
        try {
            return new ResponseEntity<>(authenticationService.validateToken(tokenValidDTO), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            e.printStackTrace(); // Добавляем логирование исключения
            return new ResponseEntity<>("Произошла ошибка при обработке запроса", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}

