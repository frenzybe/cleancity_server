package ru.frenzybe.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.entities.transaction.Transaction;
import ru.frenzybe.server.entities.transaction.TransactionType;
import ru.frenzybe.server.entities.user.User;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Все транзаеции по типу.
    List<Transaction> findAllByTransactionType(TransactionType transactionType);
    // Все транзакции по пользователю.
    List<Transaction> findAllByUserId(Long userId);
    // Транзакции по типу у пользователя.
    List<Transaction> findAllByUserIdAndTransactionType(Long user_id, TransactionType transactionType);
}