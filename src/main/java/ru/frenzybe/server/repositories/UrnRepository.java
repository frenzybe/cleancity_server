package ru.frenzybe.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.frenzybe.server.entities.Urn;

public interface UrnRepository extends JpaRepository<Urn, Long> {
    Urn findByNumber(Long number);
}