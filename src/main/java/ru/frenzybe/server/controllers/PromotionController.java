package ru.frenzybe.server.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.frenzybe.server.dto.promotion.PromotionDto;
import ru.frenzybe.server.entities.Category;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.errors.AppError;
import ru.frenzybe.server.exceptions.MessageError;
import ru.frenzybe.server.services.CategoryService;
import ru.frenzybe.server.services.PromotionService;
import ru.frenzybe.server.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promotions")
public class PromotionController {
    private final PromotionService promotionService;
    private final CategoryService categoryService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> addPromotion(@RequestBody PromotionDto promotionDto) {
        if (promotionDto.getDescription() == null
                || promotionDto.getValue() == null) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Ошибка при создании акции. Проверьте введенные значения!")
                    .build(), HttpStatus.BAD_REQUEST);
        }
        try {
            Promotion promotion = promotionService.createPromotion(promotionDto);
            return new ResponseEntity<>(promotion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPromotions() {
        try {
            return new ResponseEntity<>(promotionService.getPromotions(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPromotionById(@PathVariable Long id) {
        if (promotionService.getPromotion(id) == null) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Акция не найдена!").build(), HttpStatus.NOT_FOUND);
        }

        try {
            return new ResponseEntity<>(promotionService.getPromotion(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getPromotionByIdCategory(@PathVariable @Param("categoryId") Long categoryId) {
        Optional<Category> category = categoryService.getById(categoryId);
        if (category.isEmpty())
            return new ResponseEntity<>(MessageError.sendNotFound("Категория не найдена!"), HttpStatus.NOT_FOUND);

        try {
            return new ResponseEntity<>(promotionService.getPromotionByUserIsNullAndCategory(category.get()), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(AppError.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageError.sendInternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePromotion(@PathVariable Long id, @RequestBody PromotionDto promotionDto) {
        try {
            Promotion promotion = promotionService.updatePromotion(id, promotionDto);
            return new ResponseEntity<>(promotion, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable Long id) {
        try {
            promotionService.deletePromotion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Акция не найдена!").build(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserPromotions() {
        try{
            List<Promotion> userPromotions = promotionService.getByUser(userService.getCurrentUser());
            return new ResponseEntity<>(userPromotions, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> hidePromotion(@PathVariable Long id){
        promotionService.hidePromotion(id);
        return new ResponseEntity<>(promotionService.getPromotion(id), HttpStatus.OK);
    }
}

