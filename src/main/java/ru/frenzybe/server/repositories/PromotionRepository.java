package ru.frenzybe.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.frenzybe.server.entities.Category;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.entities.user.User;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByUserIsNullAndVisibleIsTrue();
    List<Promotion> getPromotionsByCategoryAndVisibleIsTrue(Category category);
    List<Promotion> findByCategoryAndUserIsNullAndVisibleIsTrue(Category category);
    List<Promotion> findByUserAndVisibleIsTrue(User user);
}