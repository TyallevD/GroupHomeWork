package ru.java413.grouphomework.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotBlank(message = "Имя пользователя не может быть пустым!")
    @Size(min = 3, message = "Имя пользователя должно содержать минимум 3 символа!")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым!")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов!")
    private String password;

    private String confirmPassword;

    @NotBlank(message = "Email не может быть пустым!")
    @Email(message = "Некорректный формат email!")
    private String email;

    public UserRegistrationDTO() {}

    //конструктор с полями для создания объектов
    public UserRegistrationDTO(String username, String password, String confirmPassword, String email) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
    }

    // Геттеры и сеттеры
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}