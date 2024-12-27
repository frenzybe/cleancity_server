package ru.frenzybe.server.dto.user;

import lombok.Data;
import lombok.NonNull;

/**
 * DTO for {@link ru.frenzybe.server.entities.user.User}
 */
@Data
public class SignInRequest {
    String username;
    String password;
}