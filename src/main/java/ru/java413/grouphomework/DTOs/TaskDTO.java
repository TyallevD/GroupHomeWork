package ru.java413.grouphomework.DTOs;

public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private boolean isCompleted;

    public TaskDTO(String title, String description, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
