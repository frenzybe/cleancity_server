package ru.frenzybe.server.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.frenzybe.server.entities.transaction.Transaction;
import ru.frenzybe.server.entities.user.User;
import ru.frenzybe.server.entities.user.UserRole;
import ru.frenzybe.server.exceptions.CustomException;
import ru.frenzybe.server.repositories.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Сохранение пользователя
     *
     * @return Сохраненный пользователь
     */
    public User save(User user) {
        return userRepository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + username + " не найден"));
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser () {
        // Получение аутентификации из контекста Spring Security
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверка, аутентифицирован ли пользователь
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException("Пользователь не аутентифицирован", HttpStatus.UNAUTHORIZED);
        }

        // Получение имени пользователя
        var username = authentication.getName();

        // Получение пользователя по имени
        User user = getByUsername(username);

        // Проверка, найден ли пользователь
        if (user == null) {
            throw new CustomException("Пользователь не найден", HttpStatus.NOT_FOUND);
        }

        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public void replenish(int num) {
        User user = getCurrentUser();
        user.setBalance(user.getBalance() + num);
        save(user);
    }

    public void buy(int num) {
        User user = getCurrentUser();
        user.setBalance(user.getBalance() - num);
        save(user);
    }

    /**
     * Выдача прав администратора текущему пользователю
     * Нужен для демонстрации
     */
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(UserRole.ROLE_ADMIN);
        save(user);
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
