package ru.java413.grouphomework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.java413.grouphomework.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Поиск пользователя по имени
    Optional<User> findByUsername(String username);

    // Поиск пользователя по email
    Optional<User> findByEmail(String email);

    // Проверка существования пользователя (true если существует, false если нет)
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Подсчет пользователей (возвращает общее количество записей в таблице)
    long count();
}