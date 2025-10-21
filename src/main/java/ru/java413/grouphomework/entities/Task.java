package ru.java413.grouphomework.entities;

import jakarta.persistence.*;

@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task(Long id, String title, String description, boolean completed, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.user = user;
    }

    public Task(Long id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public Task() {}

    public Long getId() { return id; }
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public boolean isCompleted() {return completed;}
    public void setCompleted(boolean completed) {this.completed = completed;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    @Override
    public String toString() {
        return "Task{id=" + id + ", title=" + title + ", description=" + description + ", completed=" + completed + "}";
    }
}
