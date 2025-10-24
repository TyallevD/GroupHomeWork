package ru.java413.grouphomework.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.java413.grouphomework.entities.Task;
import ru.java413.grouphomework.entities.User;
import ru.java413.grouphomework.services.TaskService;
import ru.java413.grouphomework.services.UserService;


import java.util.List;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

//    @GetMapping
//    public List<Task> getTasks(Authentication auth) {
//        User user = userService.getEntityByUsername(auth.getName());
//        return taskService.getUserTasks(user);
//    }

    @PutMapping("/{id}")
    public String updateTask(@PathVariable Long id,
                             @ModelAttribute Task task,
                             RedirectAttributes redirectAttributes,
                             Authentication auth) {
        try {
            User user = userService.getEntityByUsername(auth.getName());
            taskService.updateTask(id, task, user);
            redirectAttributes.addFlashAttribute("success", "Заметка успешно обновлена!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении заметки");
        }
        return "redirect:/personpage";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getEntityByUsername(auth.getName());
            taskService.deleteTask(id, user);
            redirectAttributes.addFlashAttribute("success", "Заметка успешно удалена!");
            return "redirect:/personpage";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении заметки");
        }
        return "redirect:/personpage";
    }

    @PostMapping
    public String createTask(@ModelAttribute Task task,
                             RedirectAttributes redirectAttributes) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getEntityByUsername(auth.getName());
            taskService.addTask(task, user);
            redirectAttributes.addFlashAttribute("success", "Заметка успешно создана!");
            return "redirect:/personpage";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании заметки");
        }
        return "redirect:/personpage";


    }
}
