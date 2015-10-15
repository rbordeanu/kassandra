package com.kassandra.repository;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.repository.model.Level;
import com.kassandra.repository.model.Task;
import com.kassandra.repository.model.TaskResult;
import com.kassandra.repository.model.User;

@RunWith(MockitoJUnitRunner.class)
public class TestModelTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testUser() throws IOException {
        User testUser = new User("testUserId", "Alex", "Last", "aturbatu", "aturbatu@misys.com",
                "passSecre", true);
        String serializedUser = objectMapper.writeValueAsString(testUser);
        System.out.println(serializedUser);
        User gotUser = objectMapper.readValue(serializedUser, User.class);
        assertEquals(testUser, gotUser);
    }

    @Test
    public void testTaskResult() throws IOException {
        TaskResult result = new TaskResult("testResultId", "testTaskId", "aturbatu", 0.25);
        String serializedResult = objectMapper.writeValueAsString(result);
        System.out.println(serializedResult);
        TaskResult gotResult = objectMapper.readValue(serializedResult, TaskResult.class);
        assertEquals(result, gotResult);
    }

    @Test
    public void testTask() throws IOException {
        Task task = new Task("taskId", Level.EASY, "ceva task", "aturbatu", false,
                objectMapper.createObjectNode());
        String serializedResult = objectMapper.writeValueAsString(task);
        System.out.println(serializedResult);
        Task gotTask = objectMapper.readValue(serializedResult, Task.class);
        assertEquals(task, gotTask);
    }

}
