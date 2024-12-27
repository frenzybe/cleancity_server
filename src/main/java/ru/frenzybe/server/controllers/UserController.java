package ru.frenzybe.server.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.frenzybe.server.dto.ResponseDTO;
import ru.frenzybe.server.entities.transaction.Transaction;
import ru.frenzybe.server.entities.user.User;
import ru.frenzybe.server.errors.AppError;
import ru.frenzybe.server.services.TransactionService;
import ru.frenzybe.server.services.UserService;
import ru.frenzybe.server.exceptions.CustomException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TransactionService transactionService;

    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser() {
        User currentUser;
        try {
            currentUser = userService.getCurrentUser();
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (Exception e) {
            // Логирование ошибки
            log.error("Ошибка при получении текущего пользователя", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при получении текущего пользователя");
        }

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Текущий пользователь не найден");
        }

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> listUsers() {
        try {
            List<User> allUsers = userService.getAllUsers();
            ResponseDTO response = ResponseDTO.builder()
                    .data(allUsers)
                    .message("Список всех пользователей")
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) { // используйте специфичное исключение
            // Логирование ошибки
            log.error("Ошибка при получении пользователей: {}", e.getMessage());

            AppError error = AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!")
                    .build();

            ResponseDTO response = ResponseDTO.builder()
                    .data(error)
                    .message("Не удалось получить список пользователей")
                    .build();

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(ResponseDTO.builder()
                    .message("Пользователь удален!").build(), HttpStatus.NO_CONTENT);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Пользователь не авторизован!")
                    .build(), HttpStatus.UNAUTHORIZED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Пользователь для удаления не найден!")
                    .build(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin")
    public void getAdmin() {
        userService.getAdmin();
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> listTransactions() {
        try {
            List<Transaction> transactions = transactionService.getAllTransactionForUser(userService.getCurrentUser().getId());
            return new ResponseEntity<>(ResponseDTO.builder()
                    .data(transactions)
                    .message("Транзакции текущего пользователя")
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера. " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

