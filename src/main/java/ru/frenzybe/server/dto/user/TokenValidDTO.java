package ru.frenzybe.server.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность для валидации токена")
public class TokenValidDTO {
    @Schema(description = "SCRF токен")
    String token;
}
