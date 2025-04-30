package ru.frenzybe.server.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность получения токена пользователя")
public class JwtAuthenticationResponse {
    @Schema(description = "CSRF токен", accessMode = Schema.AccessMode.READ_ONLY)
    private String token;
}
