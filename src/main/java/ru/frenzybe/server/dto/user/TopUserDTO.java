package ru.frenzybe.server.dto.user;

import lombok.Data;
import lombok.Value;
import ru.frenzybe.server.entities.user.User;

/**
 * DTO for {@link User}
 */
@Data
@Value
public class TopUserDTO {
    String username;
    Integer countOfRefills;
}