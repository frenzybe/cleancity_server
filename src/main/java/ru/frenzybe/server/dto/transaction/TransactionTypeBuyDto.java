package ru.frenzybe.server.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.entities.transaction.Transaction;
import ru.frenzybe.server.entities.transaction.TransactionType;
import ru.frenzybe.server.entities.user.User;

/**
 * DTO for {@link Transaction}
 */
@Data
@Schema(description = "Сущность транзакции покупки")
public class TransactionTypeBuyDto {
    @Schema(description = "Уникальный идентификатор акции", accessMode = Schema.AccessMode.READ_ONLY)
    private Long promotionId;
}