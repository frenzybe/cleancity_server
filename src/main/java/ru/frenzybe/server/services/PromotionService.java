package ru.frenzybe.server.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frenzybe.server.dto.promotion.PromotionDto;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.entities.user.User;
import ru.frenzybe.server.repositories.PromotionRepository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;

    public Promotion createPromotion(PromotionDto promotionDto) {
        Promotion promotion = Promotion.builder()
                .name(promotionDto.getName())
                .description(promotionDto.getDescription())
                .value(promotionDto.getValue())
                .price(promotionDto.getPrice())
                .build();

        return promotionRepository.save(promotion);
    }

    public List<Promotion> getPromotions() {
        return promotionRepository.findByUserIsNull();
    }

    public Promotion getPromotion(Long id) {
        return promotionRepository.findById(id).orElse(null);
    }

    public List<Promotion> getPromotionByName(String name) {
        return promotionRepository.getPromotionByName(name);
    }

    public Promotion updatePromotion(Long id, PromotionDto promotionDto) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (promotionDto.getName() != null) {
            promotion.setName(promotionDto.getName());
        }
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
        promotion.setExpiryDate(LocalDate.now().plusMonths(1));
        promotionRepository.save(promotion);
    }
}
