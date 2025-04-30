package ru.frenzybe.server.dto.user;

import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.Optional;

@Data
@Value
public class RatingDTO {
    List<TopUserDTO> rating;
    Optional<Long> userPositionFromRating;
}
