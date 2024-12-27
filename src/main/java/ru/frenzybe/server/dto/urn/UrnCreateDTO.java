package ru.frenzybe.server.dto.urn;

import lombok.Data;
import lombok.Value;

/**
 * DTO for {@link ru.frenzybe.server.entities.Urn}
 */
@Data
public class UrnCreateDTO {
    Long number;
    String location;
}