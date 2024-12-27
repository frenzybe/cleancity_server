package ru.frenzybe.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.frenzybe.server.entities.Promotion;
import ru.frenzybe.server.entities.transaction.Transaction;
import ru.frenzybe.server.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}