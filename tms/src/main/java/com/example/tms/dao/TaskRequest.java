package com.example.tms.dao;

import com.example.tms.models.Priority;
import com.example.tms.models.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TaskRequest {
    private String title;
    private String description;
    private Priority priority; // High, Medium, Low
    private Status status;   // To-Do, In-Progress, Completed

    private LocalDate dueDate;

    private Long userId;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public TaskRequest(String title,
                       String description,
                       Priority priority,
                       Status status,
                       LocalDate dueDate,
                       Long userId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.userId = userId;
    }

    public TaskRequest() {}
}
