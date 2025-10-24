package ru.java413.grouphomework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.java413.grouphomework.entities.User;
import ru.java413.grouphomework.services.TaskService;
import ru.java413.grouphomework.services.UserService;

@Controller
public class PersonPageController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    // Рабочая страница после входа
    @GetMapping("/personpage")
    public String personpage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        try {
            User user = userService.getUserInfo(userDetails.getUsername());
            model.addAttribute("user", user);
            model.addAttribute("welcomeMessage", "Добро пожаловать, " + user.getUsername() + "!");
            model.addAttribute("role", user.getRole());
            model.addAttribute("tasks", taskService.getUserTasks(user));
            return "personpage";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Ошибка загрузки данных пользователя!");
            return "personpage";
        }
    }
}