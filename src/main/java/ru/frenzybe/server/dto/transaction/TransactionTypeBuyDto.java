package ru.frenzybe.server.dto.transaction;

import lombok.Data;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.entities.transaction.Transaction;
import ru.frenzybe.server.entities.transaction.TransactionType;
import ru.frenzybe.server.entities.user.User;

/**
 * DTO for {@link Transaction}
 */
@Data
public class TransactionTypeBuyDto {
    private Long promotionId;
}