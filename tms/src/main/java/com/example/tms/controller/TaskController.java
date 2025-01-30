package com.example.tms.controller;

import com.example.tms.dao.TaskRequest;
import com.example.tms.models.Task;
import com.example.tms.service.task.TaskServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.findTasksByUserId(userId));
    }
}

