package ru.java413.grouphomework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.java413.grouphomework.entities.Task;
import ru.java413.grouphomework.entities.User;
import ru.java413.grouphomework.repositories.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getUserTasks(User user) {
        return taskRepository.findByUser(user);
    }

    public Task addTask(Task task, User user) {
        task.setUser(user);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id, User user) {
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Task not found or not exists."));
        taskRepository.delete(task);
    }

    public Task updateTask(Long id, Task updated, User user) {
        Task existing = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Task not found or not exists.")); /* Если такого задания нет **/
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCompleted(updated.isCompleted());
        return taskRepository.save(existing);
    }

    //Подсчёт заметок для статистики администратора
    public long tasksCount() {
        return taskRepository.count();
    }
}
