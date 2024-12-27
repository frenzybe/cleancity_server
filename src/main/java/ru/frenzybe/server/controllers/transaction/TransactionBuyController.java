package ru.frenzybe.server.controllers.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.frenzybe.server.dto.transaction.TransactionTypeBuyDto;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.entities.transaction.Transaction;
import ru.frenzybe.server.entities.transaction.TransactionType;
import ru.frenzybe.server.errors.AppError;
import ru.frenzybe.server.services.PromotionService;
import ru.frenzybe.server.services.TransactionService;
import ru.frenzybe.server.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions/purchases")
public class TransactionBuyController {
    private final TransactionService transactionService;
    private final UserService userService;


    @PostMapping
    public ResponseEntity<?> createBuyTransaction(@RequestBody TransactionTypeBuyDto transactionTypeBuyDto) {
        try {
            Transaction transaction = transactionService.createBuyTransaction(transactionTypeBuyDto);

            if (userService.getCurrentUser().getBalance() < transaction.getPrice()) {
                return new ResponseEntity<>(AppError.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Недостаточно средств.").build(), HttpStatus.BAD_REQUEST
                );
            }

            userService.buy(transaction.getPrice());
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Ошибка введенных данных. " + e).build(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO Написать обработку ошибок.
    @GetMapping
    public ResponseEntity<?> getBuyTransactions() {
        try {
            List<Transaction> transactions = transactionService.getAllTransactionByType(TransactionType.TYPE_BUY);
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
                    userService.getCurrentUser().getId(), TransactionType.TYPE_BUY);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера. " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

