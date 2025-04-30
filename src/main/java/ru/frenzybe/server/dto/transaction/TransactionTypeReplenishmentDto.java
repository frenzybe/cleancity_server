package ru.frenzybe.server.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Value;
import ru.frenzybe.server.entities.Urn;
import ru.frenzybe.server.entities.transaction.TransactionType;
import ru.frenzybe.server.entities.user.User;

/**
 * DTO for {@link ru.frenzybe.server.entities.transaction.Transaction}
 */
@Data
@Schema(description = "Сущность транзакции пополнения")
public class TransactionTypeReplenishmentDto {
    @Schema(description = "Приход", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer price;
    @Schema(description = "Уникальный идентификатор урны", accessMode = Schema.AccessMode.READ_ONLY)
    private Long urnId;
    private String timeZone;
}