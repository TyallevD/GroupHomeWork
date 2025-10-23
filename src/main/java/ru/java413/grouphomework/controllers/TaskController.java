package ru.java413.grouphomework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.java413.grouphomework.entities.Task;
import ru.java413.grouphomework.entities.User;
import ru.java413.grouphomework.services.TaskService;
import ru.java413.grouphomework.services.UserService;


import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Task> getTasks(Authentication auth) {
        User user = userService.getEntityByUsername(auth.getName());
        return taskService.getUserTasks(user);
    }

    @PostMapping
    public Task addTask(@RequestBody Task task, Authentication auth) {
        User user = userService.getEntityByUsername(auth.getName());
        return taskService.addTask(task, user);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updated, Authentication auth) {
        User user = userService.getEntityByUsername(auth.getName());
        return taskService.updateTask(id, updated, user);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id, Authentication auth) {
        User user = userService.getEntityByUsername(auth.getName());
        taskService.deleteTask(id, user);
    }
}
