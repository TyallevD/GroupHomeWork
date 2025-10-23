package ru.java413.grouphomework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.java413.grouphomework.entities.User;
import ru.java413.grouphomework.services.TaskService;
import ru.java413.grouphomework.services.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    // Панель администратора
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        Long usersCount = userService.countUsers();
        Long tasksCount = taskService.tasksCount();
        model.addAttribute("userscount", usersCount);
        model.addAttribute("taskscount", tasksCount);
        return "admin/dashboard";
    }

    // Список всех пользователей
    @GetMapping("/users")
    public String userManagement(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    // Редактирование пользователя
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findById(id);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }
}
