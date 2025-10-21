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

    // Главная страница
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("welcomeMessage", "Добро пожаловать в систему!");
        return "index";
    }

    // Страница входа
    @GetMapping("/login")
    // извлекает параметры из URL
    public String loginPage(@RequestParam(value = "error", required = false) String error, // Ошибка аутентификации
                            @RequestParam(value = "logout", required = false) String logout, //выход со своей страницы
                            @RequestParam(value = "success", required = false) String success, // упспешно зарегестрирован
                            Model model) {

        // обработка возможных ошибок и сообщений
        if (error != null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль!");
        }

        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли со своей страницы!");
        }

        if (success != null) {
            model.addAttribute("message", "Регистрация прошла успешно!");
        }

        // возвращает старницу html
        return "login";
    }

    // Страница регистрации
    // создается объект DTO и возвращается страница html
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        return "register";
    }

    // Обработка регистрации
    @PostMapping("/register")
    // происходит связывание данных формы с объектом DTO
    public String register(@ModelAttribute @Valid UserRegistrationDTO registrationDTO,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // Если есть ошибки, то возвращает на страницу регистрации
            return "register";
        }

        // Проверка на совпадение паролей, если есть ошибки, то возвращает на страницу регистрации
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            model.addAttribute("error", "Введенные пароли не совпадают! Попробуйте ще раз.");
            return "register";
        }

        try {
            User user = userService.registerUser(registrationDTO);
            redirectAttributes.addFlashAttribute("success",
                    "Регистрация прошла успешно! Теперь вы можете войти в систему.");

            // переход на страницу входа после успешной регистрации
            return "redirect:/login?success=true";
        } catch (RuntimeException e) {
            // обработка возможных ошибок
            model.addAttribute("error", e.getMessage());
            model.addAttribute("userRegistrationDTO", registrationDTO);
            return "register";
        }
    }

    // Страница отказа в доступе
    @GetMapping("/access-denied")
    // Метод должен срабатывать тогда, когда пользователь переходит на страницу, к которой нет доступа.
    // Например, обычный пользователь пытается перейти на страницу, к которой есть доступ только у админа
    public String accessDenied() {
        return "access-denied";
    }
}

// как работает регистрация:
// пользователь заходит на страницу (/register) и видит форму для заполнения
// после заполнение форма отправляется и данные связываются с DTO
// происходит проверка
// пользователь сохраняется в базу данных
// если все ок, то переход на страницу
// если не ок, то сообщения с ошибкой