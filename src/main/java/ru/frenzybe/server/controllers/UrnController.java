package ru.frenzybe.server.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.frenzybe.server.dto.ResponseDTO;
import ru.frenzybe.server.dto.urn.UrnDTO;
import ru.frenzybe.server.entities.Urn;
import ru.frenzybe.server.errors.AppError;
import ru.frenzybe.server.services.UrnService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/urns")
@RequiredArgsConstructor
public class UrnController {

    private final UrnService urnService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UrnDTO urnCreateDTO) {
        try {
            return new ResponseEntity<>(urnService.create(urnCreateDTO), HttpStatus.CREATED);
        } catch (PropertyValueException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Ошибка: отправленные данные имеют не верный формат.")
                    .build(), HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Ошибка при создании уры.")
                    .build(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Неопредеделенная ошибка сервера")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Urn> urns = urnService.getAll();
            return new ResponseEntity<>(ResponseDTO.builder()
                    .data(urns)
                    .message("Все урны")
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка при получении всех урн."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Urn urn = urnService.getById(id);
            if (urn == null) {
                throw new EntityNotFoundException("Урна c номером " + id + " не найдена.");
            }
            return new ResponseEntity<>(ResponseDTO.builder()
                    .data(urn)
                    .message("Урна").build(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(AppError.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value()).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка при чтении урны")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UrnDTO urnDto) {
        try {
            Urn updatedUrn = urnService.updateUrnLocation(id, urnDto);
            return new ResponseEntity<>(updatedUrn, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Ошибка при поиске объекта").build(),
                    HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!").build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            urnService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Объект с таким номером не найден")
                    .build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(AppError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Ошибка сервера!")
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

