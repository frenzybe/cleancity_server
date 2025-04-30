package ru.frenzybe.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.frenzybe.server.entities.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getByName(String name);
    boolean findByName(String name);
    void deleteByName(String name);
}