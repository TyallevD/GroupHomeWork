//package ru.java413.grouphomework.DTOs;
//
//import jakarta.validation.constraints.NotBlank;
//
//public class LoginDTO {
//
//    // проверка на то, что поле заполнено
//    @NotBlank(message = "Имя пользователя не может быть пустым!")
//    private String username;
//
//    // проверка на то, что поле заполнено
//    @NotBlank(message = "Пароль не может быть пустым!")
//    private String password;
//
//    public LoginDTO() {}
//
//    // конструктор с параметрами для создания объектов
//    public LoginDTO(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    // Геттеры и сеттеры
//    public String getUsername() { return username; }
//    public void setUsername(String username) { this.username = username; }
//
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//}