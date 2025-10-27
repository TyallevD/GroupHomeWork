package ru.java413.grouphomework.DTOs;

// класс используется для показа информации о пользователе из API
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private Boolean isEnabled;

    public UserResponseDTO() {}



    // для создания объектов
    public UserResponseDTO(String username, String email, String role , Boolean isEnabled) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.isEnabled = isEnabled;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", isEnabled=" + isEnabled;
    }
}