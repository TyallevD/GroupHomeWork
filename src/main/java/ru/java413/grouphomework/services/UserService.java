package ru.java413.grouphomework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.java413.grouphomework.DTOs.UserRegistrationDTO;
import ru.java413.grouphomework.DTOs.UserResponseDTO;
import ru.java413.grouphomework.entities.User;
import ru.java413.grouphomework.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService /* <- UserDetailsService ключевой компонент для авторизации
 и аутетинтификации**/{

    @Autowired
    private UserRepository userRepository;

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

    // Аутентификация пользователя
    public boolean authenticateUser(String username, String password) {
        try {
            User user = findByUsername(username);
            return passwordEncoder.matches(password, user.getPassword());
        } catch (RuntimeException e) {
            return false;
        }
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

    // Метод для преобразования User в UserResponseDTO
    public UserResponseDTO convertToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}