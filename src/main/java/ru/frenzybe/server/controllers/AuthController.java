package ru.frenzybe.server.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.frenzybe.server.dto.user.SignInRequest;
import ru.frenzybe.server.dto.user.SignUpRequest;
import ru.frenzybe.server.dto.user.TokenValidDTO;
import ru.frenzybe.server.exceptions.CustomException;
import ru.frenzybe.server.services.AuthenticationService;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Аутентификация", description = "Регистрация, авторизация пользователей, проверка токена")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Авторизация пользователя",
            description = "Метод для авторизации пользователя с использованием учетных данных."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация"),
            @ApiResponse(responseCode = "400", description = "Неверные учетные данные"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping(value = "/sign-in")
    public ResponseEntity<?> signIn(
            @Validated @RequestBody
            @Parameter(description = "Сущность авторизации пользователя", required = true) SignInRequest request
    ) {
        try {
            return new ResponseEntity<>(authenticationService.signIn(request), HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Произошла ошибка при обработке запроса", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(
            @Validated @RequestBody
            @Parameter(description = "Сущность регистрации пользователя", required = true) SignUpRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(authenticationService.signUp(request), HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            return new ResponseEntity<>("Произошла ошибка при обработке запроса", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Валидация токена",
            description = "Проверка токена на его активность"
    )
    @PostMapping("/validate_token")
    public ResponseEntity<?> validateToken(
            @RequestBody @Parameter(description = "Сущность для валидации токена", required = true) TokenValidDTO tokenValidDTO
    ) {
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

