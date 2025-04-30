package ru.frenzybe.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.frenzybe.server.dto.user.RatingDTO;
import ru.frenzybe.server.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> findTop3ByOrderByCountOfRefillsDesc();

    @Query(value="select count(u) from User u where u.countOfRefills >= ?1 and u.id <> ?2")
    Optional<Long> countBetterUsers(int currentCount, Long currentUserId);
}