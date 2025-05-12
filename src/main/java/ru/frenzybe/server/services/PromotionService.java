package ru.frenzybe.server.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frenzybe.server.dto.promotion.PromotionDto;
import ru.frenzybe.server.entities.Category;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.entities.user.User;
import ru.frenzybe.server.repositories.PromotionRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final CategoryService categoryService;

    public Promotion createPromotion(PromotionDto promotionDto) {
        Promotion promotion = Promotion.builder()
                .description(promotionDto.getDescription())
                .value(promotionDto.getValue())
                .price(promotionDto.getPrice())
                .visible(true)
                .build();

        if (promotionDto.getCategoryId() != null) {
            Category category = categoryService.getById(promotionDto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Категория не найдена"));
            promotion.setCategory(category);
        }

        return promotionRepository.save(promotion);
    }

    public List<Promotion> getPromotions() {
        return promotionRepository.findByUserIsNullAndVisibleIsTrue();
    }

    public Promotion getPromotion(Long id) {
        return promotionRepository.findById(id).orElse(null);
    }

    public List<Promotion> getPromotionByUserIsNullAndCategory(Category category) {
        return promotionRepository.findByCategoryAndUserIsNullAndVisibleIsTrue(category);
    }

    public Promotion updatePromotion(Long id, PromotionDto promotionDto) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (promotionDto.getDescription() != null) {
            promotion.setDescription(promotionDto.getDescription());
        }
        if (promotionDto.getValue() != null) {
            promotion.setValue(promotionDto.getValue());
        }
        return promotionRepository.save(promotion);
    }

    public void deletePromotion(Long id) {
        if (!promotionRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        promotionRepository.deleteById(id);
    }

    public void buyPromotion(User user, Promotion promotion) {
        promotion.setUser(user);
        promotionRepository.save(promotion);
    }

    public List<Promotion> getByCategory(Category category){
        return promotionRepository.getPromotionsByCategoryAndVisibleIsTrue(category);
    }

    public List<Promotion> getByUser(User user){
        return promotionRepository.findByUserAndVisibleIsTrue(user);
    }

    public void hidePromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        promotion.setVisible(false);
        promotionRepository.save(promotion);
    }
}
