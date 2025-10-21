package ru.java413.grouphomework.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.java413.grouphomework.DTOs.UserRegistrationDTO;
import ru.java413.grouphomework.entities.User;
import ru.java413.grouphomework.services.UserService;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Главная страница - авторизация
    @GetMapping({"/", "/home", "/login"})
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "success", required = false) String success,
                            Model model) {

        if (error != null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль!");
        }

        if (logout != null) {
            model.addAttribute("logout", "Вы успешно вышли из системы!");
        }

        if (success != null) {
            model.addAttribute("message", "Регистрация прошла успешно! Теперь вы можете войти.");
        }

        return "index";
    }

    // Страница регистрации
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        return "register";
    }

    // Обработка регистрации
    @PostMapping("/register")
    public String register(@ModelAttribute @Valid UserRegistrationDTO registrationDTO,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            model.addAttribute("error", "Введенные пароли не совпадают! Попробуйте еще раз.");
            return "register";
        }

        try {
            User user = userService.registerUser(registrationDTO);
            redirectAttributes.addFlashAttribute("success",
                    "Регистрация прошла успешно! Теперь вы можете войти в систему.");
            return "redirect:/?success=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("userRegistrationDTO", registrationDTO);
            return "register";
        }
    }
}

// как работает регистрация:
// пользователь заходит на страницу (/register) и видит форму для заполнения
// после заполнение форма отправляется и данные связываются с DTO
// происходит проверка
// пользователь сохраняется в базу данных
// если все ок, то переход на страницу
// если не ок, то сообщения с ошибкой