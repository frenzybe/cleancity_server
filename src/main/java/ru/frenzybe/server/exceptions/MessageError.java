package ru.frenzybe.server.exceptions;

import org.springframework.http.HttpStatus;
import ru.frenzybe.server.errors.AppError;

public class MessageError {
    String message;

    public static AppError sendInternalError(){
        return AppError.builder()
                .message("Ошибка сервера!")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }

    public static AppError sendNotFound(String message){
        return AppError.builder()
                .message(message)
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }
}
