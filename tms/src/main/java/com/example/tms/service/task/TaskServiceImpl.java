package com.example.tms.service.task;

import com.example.tms.dao.TaskRequest;
import com.example.tms.models.Task;
import com.example.tms.repositories.TaskRepository;
import com.example.tms.service.user.UserServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TaskServiceImpl  {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(TaskRequest request) {

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        if(request.getUserId() != null) {
            task.setUser(userService.findById(request.getUserId()));
        }
        return taskRepository.save(task);
    }

    public List<Task> findTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

}
