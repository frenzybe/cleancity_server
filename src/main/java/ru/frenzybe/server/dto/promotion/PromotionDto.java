package ru.frenzybe.server.dto.promotion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.frenzybe.server.entities.Category;

/**
 * DTO for {@link ru.frenzybe.server.entities.Promotion}
 */
@Data
@Schema(description = "Сущность акции")
public class PromotionDto {
    private String description;
    private Long categoryId;
    private String value;
    private int price = 0;
}