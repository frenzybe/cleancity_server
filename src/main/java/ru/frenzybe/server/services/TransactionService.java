package ru.frenzybe.server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frenzybe.server.dto.transaction.TransactionTypeBuyDto;
import ru.frenzybe.server.dto.transaction.TransactionTypeReplenishmentDto;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.entities.transaction.Transaction;
import ru.frenzybe.server.entities.transaction.TransactionType;
import ru.frenzybe.server.entities.user.User;
import ru.frenzybe.server.repositories.TransactionRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final UrnService urnService;
    private final PromotionService promotionService;

    public Transaction createReplenishTransaction(TransactionTypeReplenishmentDto transactionTypeReplenishmentDto) {
        Transaction transaction = Transaction.builder()
                .user(userService.getCurrentUser())
                .urn(urnService.getById(transactionTypeReplenishmentDto.getUrnId()))
                .transactionType(TransactionType.TYPE_REPLENISHMENT)
                .price(transactionTypeReplenishmentDto.getPrice())
                .build();

        return transactionRepository.save(transaction);
    }

    public Transaction createBuyTransaction(TransactionTypeBuyDto transactionTypeBuyDto) {
        Promotion promotion = promotionService.getPromotion(transactionTypeBuyDto.getPromotionId());

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.TYPE_BUY)
                .user(userService.getCurrentUser())
                .price(promotion.getPrice())
                .promotion(promotionService.getPromotion(transactionTypeBuyDto.getPromotionId()))
                .build();

        if (transaction.getPromotion() != null) {
            promotionService.buyPromotion(userService.getCurrentUser(), promotion);
        }

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactionByType(TransactionType transactionType) {
        return transactionRepository.findAllByTransactionType(transactionType);
    }

    public List<Transaction> getAllTransactionForUser(Long userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    public List<Transaction> getTransactionForUserByType(Long userId, TransactionType transactionType) {
        return transactionRepository.findAllByUserIdAndTransactionType(userId, transactionType);
    }
}
