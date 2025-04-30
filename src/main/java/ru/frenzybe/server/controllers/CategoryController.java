package ru.frenzybe.server.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.frenzybe.server.dto.category.CategoryDto;
import ru.frenzybe.server.entities.Category;
import ru.frenzybe.server.errors.AppError;
import ru.frenzybe.server.exceptions.MessageError;
import ru.frenzybe.server.services.CategoryService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {
        if (categoryDto.getName() == null || categoryDto.getDescription() == null ||
                categoryDto.getConditions() == null) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Ошибка при создании категории. Проверьте введенные значения!")
                    .build(), HttpStatus.BAD_REQUEST);
        }
        try {
            Category category = categoryService.createCategory(categoryDto);
            return new ResponseEntity<>(category, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageError.sendInternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        try {
            return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageError.sendInternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getById(id);
        if (category.isEmpty()) {
            return new ResponseEntity<>(MessageError.sendNotFound("Категория не найдена!"), HttpStatus.NOT_FOUND);
        }
        try{
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(MessageError.sendInternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name){
        Category category = categoryService.getByName(name);
        if(category == null){
            return new ResponseEntity<>(MessageError.sendNotFound("Категория не найдена!"), HttpStatus.NOT_FOUND);
        }
        try{
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageError.sendInternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto){
        try{
            return new ResponseEntity<>(categoryService.updateCategory(id, categoryDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageError.sendInternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{name}")
    public ResponseEntity<?> updateCategory(@PathVariable String name, @RequestBody CategoryDto categoryDto){
        Category category = categoryService.getByName(name);
        if(category == null){
            return new ResponseEntity<>(MessageError.sendNotFound("Категория не найдена!"), HttpStatus.NOT_FOUND);
        }
        try{
            return new ResponseEntity<>(categoryService.updateCategory(category.getId(), categoryDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageError.sendInternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        try{
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(EntityNotFoundException e){
            return new ResponseEntity<>(MessageError.sendNotFound("Категория не найдена!"), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(MessageError.sendInternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

