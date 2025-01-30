package com.example.tms.service;

import com.example.tms.dao.TaskRequest;
import com.example.tms.models.Priority;
import com.example.tms.models.Status;
import com.example.tms.models.Task;
import com.example.tms.models.User;
import com.example.tms.repositories.TaskRepository;
import com.example.tms.service.task.TaskServiceImpl;
import com.example.tms.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private  TaskRequest request;
    private User mockUser;
    private Task mockTask;

    public TaskServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        request = new TaskRequest();
        request.setTitle("Test Task");
        request.setDescription("Task Description");
        request.setStatus(Status.TO_DO);
        request.setPriority(Priority.HIGH);
        request.setDueDate(LocalDate.now().plusDays(5));
        request.setUserId(1L);

        mockUser = new User();
        mockUser.setId(1L);

        mockTask = new Task();
        mockTask.setTitle(request.getTitle());
        mockTask.setDescription(request.getDescription());
        mockTask.setStatus(request.getStatus());
        mockTask.setPriority(request.getPriority());
        mockTask.setDueDate(request.getDueDate());
        mockTask.setUser(mockUser);
    }

    @Test
    void testCreateTask_Success() {
        when(userService.findById(1L)).thenReturn(mockUser);
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        Task createdTask = taskService.createTask(request);

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        assertEquals(Status.TO_DO, createdTask.getStatus());
        assertEquals(Priority.HIGH, createdTask.getPriority());
        assertEquals(mockUser, createdTask.getUser());

        verify(userService, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

}
