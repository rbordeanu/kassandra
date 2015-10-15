package com.kassandra.repository;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.repository.model.*;

@RunWith(MockitoJUnitRunner.class)
public class TestModelTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testUser() throws IOException {
        User testUser = new User(
                "testUserId",
                "Alex",
                "Last",
                "aturbatu",
                "aturbatu@misys.com",
                "passSecre",
                "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfp1/v/t1.0-1/c28.116.675.675/s160x160/11822746_1085060741521915_7892215979629432132_n.jpg?oh=d97a2df88054b86d22fa3c5923e890e3&oe=569895CD&__gda__=1451952774_e86712f063ffe82e12d24d4ccb57962f",
                true);
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
        Task task = new Task("taskId", "Easy Test", Level.EASY, "ceva task", "aturbatu", false,
                objectMapper.createObjectNode(), 3600000, Arrays.asList("algorithms",
                "dataStructures"));
        String serializedResult = objectMapper.writeValueAsString(task);
        System.out.println(serializedResult);
        Task gotTask = objectMapper.readValue(serializedResult, Task.class);
        assertEquals(task, gotTask);
    }

    @Test
    public void testTest() throws IOException {
        Question firstQ = new Question("1",
                "What will happen if we put a key object in a HashMap which is already there ?", 0,
                Arrays.asList("Replace the old object",
                        "An exception is thrown",
                        "The map contains only the first object",
                        "The map contains both objects"));

        List<Question> questionList = Lists.newArrayList();
        questionList.add(firstQ);

        com.kassandra.repository.model.Test test = new com.kassandra.repository.model.Test(questionList);

        String serializedResult = objectMapper.writeValueAsString(test);
        System.out.println(serializedResult);

        com.kassandra.repository.model.Test gotTest = objectMapper.readValue(serializedResult, com.kassandra.repository.model.Test.class);
        assertEquals(test, gotTest);
    }

}
