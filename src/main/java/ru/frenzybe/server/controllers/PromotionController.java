package ru.frenzybe.server.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.frenzybe.server.dto.promotion.PromotionDto;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.errors.AppError;
import ru.frenzybe.server.services.PromotionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping
    public ResponseEntity<?> addPromotion(@RequestBody PromotionDto promotionDto) {
        if (promotionDto.getName() == null || promotionDto.getDescription() == null
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

    @GetMapping("name/{name}")
    public ResponseEntity<?> getPromotionByName(@PathVariable String name) {
        if (promotionService.getPromotionByName(name) == null) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Акция не найдена!").build(), HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(promotionService.getPromotionByName(name), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePromotion(@PathVariable Long id, @RequestBody PromotionDto promotionDto) {
        try{
            Promotion promotion =  promotionService.updatePromotion(id, promotionDto);
            return new ResponseEntity<>(promotion, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable Long id){
        try{
            promotionService.deletePromotion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(EntityNotFoundException e){
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Акция не найдена!").build(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

