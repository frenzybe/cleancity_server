package ru.frenzybe.server.dto.urn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for {@link ru.frenzybe.server.entities.Urn}
 */
@Data
@Schema(description = "Сущность урны")
public class UrnDTO {
    @Schema(description = "Номер урны", accessMode = Schema.AccessMode.READ_ONLY)
    Long number;
    @Schema(description = "Местоположение урны", accessMode = Schema.AccessMode.READ_ONLY)
    String location;
}