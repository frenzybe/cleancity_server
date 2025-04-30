package ru.frenzybe.server.dto.promotion;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;
import ru.frenzybe.server.entities.Category;

/**
 * DTO for {@link ru.frenzybe.server.entities.Promotion}
 */
@Data
@Schema(description = "Сущность акции")
public class PromotionDto {
    @Schema(description = "Наименование акции", accessMode = Schema.AccessMode.READ_ONLY)
    private String name;
    @Nullable
    @Schema(description = "Описание акции", accessMode = Schema.AccessMode.READ_ONLY)
    private String description;
    @Schema(description = "Уникальный идентификатор категории", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long category;
    @Schema(description = "Ключ акции (промокод)", example = "Hf7rHmji6!2", accessMode = Schema.AccessMode.READ_ONLY)
    private String value;
    @Schema(description = "Стоимость акции", accessMode = Schema.AccessMode.READ_ONLY)
    private int price = 0;
}