package ru.frenzybe.server.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link ru.frenzybe.server.entities.Category}
 */
@Data
@Schema(description = "Сущность категории")
public class CategoryDto {
    @Schema(description = "Наименование категории акции", example = "VK Music", accessMode = Schema.AccessMode.READ_ONLY)
    String name;

    @Schema(description = "Описание категории акции", example = "Скидка и подписка бесплатно на VK Music", accessMode = Schema.AccessMode.READ_ONLY)
    String description;

    @Schema(description = "Условие акции", example = "100 бонусов - 3 месяца подписки на VK Music", accessMode = Schema.AccessMode.READ_ONLY)
    String conditionsPromotion;
    String termsOfUse;
}