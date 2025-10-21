package ru.java413.grouphomework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.java413.grouphomework.entities.User;
import ru.java413.grouphomework.services.UserService;

@Controller
public class PersonPageController {

    @Autowired
    private UserService userService;

    // Личный кабинет пользователя (personpage)
    @GetMapping("/personpage")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        try {
            // возвращется конкретный объект
            User user = userService.getUserInfo(userDetails.getUsername());
            model.addAttribute("user", user);
            model.addAttribute("welcomeMessage", "Добро пожаловать, " + user.getUsername() + "!");
            return "personpage";
        } catch (RuntimeException e) {
            // возвраащется конкретный объект, но пользователь также видит сообщение об ошибке
            model.addAttribute("error", "Ошибка загрузки данных пользователя!");
            return "personpage";
        }
    }
}
