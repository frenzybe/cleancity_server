package ru.frenzybe.server.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link ru.frenzybe.server.entities.user.User}
 */
@Data
@Schema(description = "Сущность авторизации пользователя")
public class SignInRequest {
    @Schema(description = "Никнейм пользователя")
    String username;
    @Schema(description = "Пароль пользователя")
    String password;
}