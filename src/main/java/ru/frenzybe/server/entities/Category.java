package ru.frenzybe.server.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Объект категории
 */
@Builder
@Getter
@Setter
@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String description;
    // Условие акции.
    private String conditions;
}