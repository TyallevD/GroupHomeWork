package ru.java413.grouphomework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.java413.grouphomework.DTOs.UserResponseDTO;
import ru.java413.grouphomework.entities.User;
import ru.java413.grouphomework.services.TaskService;
import ru.java413.grouphomework.services.UserService;

import java.util.List;

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
        return "/admin/users";
    }

    // Редактирование пользователя
    @PutMapping("/users/{id}")
    public String editUser(@PathVariable Long id,
                           @ModelAttribute UserResponseDTO userResponseDTO,
                           RedirectAttributes redirectAttributes,
                           Authentication auth) {
        if (!auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            redirectAttributes.addAttribute("error", "Недостаточно прав");
            return "redirect:/admin/users";
        }
        try {
            String currentUserName = auth.getName();
            User currentUser = userService.findByUsername(currentUserName);
            if (currentUser.getId().equals(id)) {
                redirectAttributes.addFlashAttribute("error", "Нельзя редактировать собственный аккаунт через панель администратора");
                return "redirect:/admin/users";
            }
            System.out.println("Что-то меняется?");
            userService.updateUser(id, userResponseDTO);
            redirectAttributes.addFlashAttribute("success", "Пользователь успешно обновлен");
            return "redirect:/admin/users";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(   "error", "Ошибка при изменении пользователя");
        }
        return "redirect:/admin/users";
    }
}
