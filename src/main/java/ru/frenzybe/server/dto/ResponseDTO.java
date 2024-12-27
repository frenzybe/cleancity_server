package ru.frenzybe.server.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {
    private Object data;
    private String message;
}
