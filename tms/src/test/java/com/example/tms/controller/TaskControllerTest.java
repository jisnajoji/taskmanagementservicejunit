package com.example.tms.controller;

import com.example.tms.dao.TaskRequest;
import com.example.tms.models.Priority;
import com.example.tms.models.Status;
import com.example.tms.models.Task;
import com.example.tms.service.task.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskServiceImpl taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    public void testCreateTask_Success() {
        TaskRequest taskRequest = new TaskRequest("title","description", Priority.HIGH, Status.TO_DO, LocalDate.now(),1L);
//        Task taskResponse = new Task()
    }
}
