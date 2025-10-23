package ru.java413.grouphomework.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.java413.grouphomework.entities.User;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public DatabaseUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // загрузка пользователя из базы данных
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь: " + username + " не найден!");
        }

        String role = user.getRole();
        if (role == null) {
            role = "ROLE_USER";
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(role.replace("ROLE_", "")) // устанавливается роль пользователя
                .build();
    }
}