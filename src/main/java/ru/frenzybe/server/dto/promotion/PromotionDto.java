package ru.frenzybe.server.dto.promotion;

import jakarta.annotation.Nullable;
import lombok.Data;

/**
 * DTO for {@link ru.frenzybe.server.entities.Promotion}
 */
@Data
public class PromotionDto {
    private String name;
    private String description;
    private String value;
    private int price = 0;
}