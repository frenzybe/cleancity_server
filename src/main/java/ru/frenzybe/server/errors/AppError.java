package ru.frenzybe.server.errors;

import lombok.*;
import org.springframework.http.HttpStatusCode;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppError {
    private int status;
    private String message;
}
