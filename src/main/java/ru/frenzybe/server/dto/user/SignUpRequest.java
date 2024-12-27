package ru.frenzybe.server.dto.user;

import lombok.Data;
import lombok.NonNull;

/**
 * DTO for {@link ru.frenzybe.server.entities.user.User}
 */
@Data
@NonNull
public class SignUpRequest {
    String username;
    String password;
    String email;
}