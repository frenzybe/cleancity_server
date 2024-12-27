package ru.frenzybe.server.controllers.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.frenzybe.server.dto.transaction.TransactionTypeReplenishmentDto;
import ru.frenzybe.server.entities.transaction.Transaction;
import ru.frenzybe.server.entities.transaction.TransactionType;
import ru.frenzybe.server.errors.AppError;
import ru.frenzybe.server.services.TransactionService;
import ru.frenzybe.server.services.UrnService;
import ru.frenzybe.server.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions/replenishments")
@RequiredArgsConstructor
public class TransactionReplenishController {

    private final TransactionService transactionService;
    private final UserService userService;
    private final UrnService urnService;

    @PostMapping
    public ResponseEntity<?> createReplenishTransaction(@RequestBody TransactionTypeReplenishmentDto transactionTypeReplenishmentDto) {
        if (transactionTypeReplenishmentDto.getPrice() == null ||
                transactionTypeReplenishmentDto.getPrice() <= 0 || transactionTypeReplenishmentDto.getUrnId() == null) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Ошибка введенных данных").build(), HttpStatus.BAD_REQUEST);
        }
        if (urnService.getById(transactionTypeReplenishmentDto.getUrnId()) == null) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Урна с ID: " + transactionTypeReplenishmentDto.getUrnId() + " не найдена.")
                    .build(), HttpStatus.NOT_FOUND);
        }
        try {
            Transaction transaction = transactionService.createReplenishTransaction(transactionTypeReplenishmentDto);
            userService.replenish(transactionTypeReplenishmentDto.getPrice());
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Ошибка введенных данных."), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO Написать обработку ошибок.
    @GetMapping
    public ResponseEntity<?> getReplenishTransactions() {
        try {
            List<Transaction> transactions = transactionService.getAllTransactionByType(TransactionType.TYPE_REPLENISHMENT);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера. " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO Написать обработку ошибок.
    @GetMapping("/user")
    public ResponseEntity<?> getBuyUserTransactions() {
        try {
            List<Transaction> transactions = transactionService.getTransactionForUserByType(
                    userService.getCurrentUser().getId(), TransactionType.TYPE_REPLENISHMENT);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера. " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

