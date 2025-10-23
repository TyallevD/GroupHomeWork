package ru.java413.grouphomework.initializer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.java413.grouphomework.entities.User;
import ru.java413.grouphomework.repositories.UserRepository;

//Компонент для создания дефолтного администратора, ниже приведены username и password
@Component
public class AdminInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        createAdminUser();
    }

    private void createAdminUser() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@admin.ru");
            admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);

            userRepository.save(admin);
            System.out.println("Создан пользователь \"admin\" с паролем \"admin\"");
        }
    }
}
