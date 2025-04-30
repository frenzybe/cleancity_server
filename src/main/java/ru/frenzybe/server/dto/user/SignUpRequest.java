package ru.frenzybe.server.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link ru.frenzybe.server.entities.user.User}
 */
@Data
@Schema(description = "Сущность регистрации пользователя")
public class SignUpRequest {
    @Schema(description = "Никнейм пользователя")
    String username;
    @Schema(description = "Пароль пользователя")
    String password;
    @Schema(description = "Почта пользователя")
    String email;
}