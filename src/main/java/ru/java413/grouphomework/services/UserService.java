package ru.java413.grouphomework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.java413.grouphomework.DTOs.UserRegistrationDTO;
import ru.java413.grouphomework.entities.User;
import ru.java413.grouphomework.repositories.TaskRepository;
import ru.java413.grouphomework.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Регистрация нового пользователя
    public User registerUser(String username, String password, String email) {
        // Проверка, существует ли данный пользователь
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Пользователь с именем '" + username + "' уже существует!");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Пользователь с email '" + email + "' уже существует!");
        }

        validateUserData(username, password, email);

        // Создание и сохранение пользователя
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRole("ROLE_USER"); //роль по умолчанию
        user.setEnabled(true);

        return userRepository.save(user);
    }

    // Регистрация нового пользователя
    public User registerUser(UserRegistrationDTO registrationDTO) {
        return registerUser(
                registrationDTO.getUsername(),
                registrationDTO.getPassword(),
                registrationDTO.getEmail()
        );
    }

    // Валидация данных пользователя
    private void validateUserData(String username, String password, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Имя пользователя не может быть пустым!");
        }

        if (username.length() < 3) {
            throw new RuntimeException("Имя пользователя должно содержать минимум 3 символа!");
        }

        if (password == null || password.length() < 6) {
            throw new RuntimeException("Пароль должен содержать минимум 6 символов!");
        }

        if (email == null || !isValidEmail(email)) {
            throw new RuntimeException("Некорректный email адрес!");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    // Поиск пользователя по имени
    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("Пользователь с именем '" + username + "' не найден!");
        }
    }

    // Получение информации о пользователе
    public User getUserInfo(String username) {
        return findByUsername(username);
    }


    public User getEntityByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    //Функционал администратора
    //Смена роли пользователя
    public void changeUserRole(Long userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Проверяем валидность роли
        if (!isValidRole(newRole)) {
            throw new RuntimeException("Недопустимая роль: " + newRole);
        }

        user.setRole(newRole);
        userRepository.save(user);
    }

    //Метод для проверки валидности роли (выше в методе изменения роли)
    private boolean isValidRole(String role) {
        return role != null && (role.equals("ROLE_USER") || role.equals("ROLE_ADMIN"));
    }

    //Список всех пользователей
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    //Поиск по id для изменения роли
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    //Подсчёт пользователей для статистики администратора
    public long countUsers() {
        return userRepository.count();
    }
}