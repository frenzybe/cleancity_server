package ru.frenzybe.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.frenzybe.server.entities.Category;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> getPromotionByName(String name);
    List<Promotion> findByUserIsNull();
    List<Promotion> getPromotionsByCategory(Category category);
    List<Promotion> findByUserIsNullAndCategory(Category category);
}