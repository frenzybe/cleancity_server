package ru.frenzybe.server.dto.transaction;

import lombok.Data;
import lombok.Value;
import ru.frenzybe.server.entities.Urn;
import ru.frenzybe.server.entities.transaction.TransactionType;
import ru.frenzybe.server.entities.user.User;

/**
 * DTO for {@link ru.frenzybe.server.entities.transaction.Transaction}
 */
@Data
public class TransactionTypeReplenishmentDto {
    private Integer price; // TODO: Maybe unused
    private Long urnId;
}