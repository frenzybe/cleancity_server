package ru.frenzybe.server.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frenzybe.server.dto.category.CategoryDto;
import ru.frenzybe.server.entities.Category;
import ru.frenzybe.server.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto) {
        Category category = Category.builder()
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .conditions(categoryDto.getConditions())
                .build();
        return categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getByName(String name) {
        if (categoryRepository.findByName(name))
            return categoryRepository.getByName(name);
        else return null;
    }

    public Optional<Category> getById(Long id){
        return categoryRepository.findById(id);
    }

    public Category updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (categoryDto.getName().isEmpty()) {
            category.setName(category.getName());
        }
        if (categoryDto.getDescription().isEmpty()) {
            category.setDescription(category.getDescription());
        }
        if (categoryDto.getConditions().isEmpty()) {
            category.setConditions(category.getConditions());
        }
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) throw new EntityNotFoundException();
        categoryRepository.deleteById(id);
    }

    public void deleteCategory(String name){
        if (!categoryRepository.findByName(name)) throw new EntityNotFoundException();
        categoryRepository.deleteByName(name);
    }
}
