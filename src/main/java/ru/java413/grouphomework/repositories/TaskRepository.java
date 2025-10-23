package ru.java413.grouphomework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.java413.grouphomework.entities.Task;
import ru.java413.grouphomework.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Возвращает задачи конкретного пользователя
    List<Task> findByUser(User user);

    // Для безопасности - можно подключить задачу по id и user
    Optional<Task> findByIdAndUser(Long id, User user);
}
