package com.kassandra.repository.impl;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.repository.*;
import com.kassandra.repository.model.Task;
import com.kassandra.repository.model.User;
import org.slf4j.Logger;

public class TaskRepository implements ITaskRepository {

    private static final Logger LOG = getLogger(TaskRepository.class);
    private final String TASK_COLLECTION = "task";
    private final String TASK_RESULT_COLLECTION = "taskResult";
    private final IMongoDbProvider mongoDbProvider;
    private final ITaskResultRepository taskResultRepository;

    public TaskRepository(IMongoDbProvider mongoDbProvider, ITaskResultRepository taskResultRepository) {
        this.mongoDbProvider = mongoDbProvider;
        this.taskResultRepository = taskResultRepository;
    }

    @Override
    public Task getTask(String _id) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(TASK_COLLECTION);
        String taskJson = mongoDbClient.getObjectById(_id);
        if (taskJson == null) {
            throw new RepositoryException(String.format("No document with id %s found", _id));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(taskJson, Task.class);
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }
    }

    @Override
    public List<Task> getAll() throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(TASK_COLLECTION);
        List<String> taskListJson = mongoDbClient.getAll();
        List<Task> taskList = new ArrayList();
        if (taskListJson.isEmpty()) {
            throw new RepositoryException(String.format("No document with type task found"));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            for(String taskJson  : taskListJson) {
                 Task task =  objectMapper.readValue(taskJson, Task.class);
                 taskList.add(task);
            }
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }

        return taskList;
    }

    @Override
    public List<Task> getAvailableTasks(String userId) throws RepositoryException {

        IMongoDbClient mongoDbClient = mongoDbProvider.create(TASK_COLLECTION);

        List<String> taskListJson = mongoDbClient.getAll();
        List<Task> taskList = new ArrayList();
        if (taskListJson.isEmpty()) {
            throw new RepositoryException(String.format("No document with type task found"));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            for(String taskJson  : taskListJson) {
                Task task =  objectMapper.readValue(taskJson, Task.class);
                taskList.add(task);
            }
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }

        HashMap<String, String> resultTasks = taskResultRepository.getAllByUser(userId);
        for(String resultTask : resultTasks.values()) {
            taskList.remove(resultTask);
        }

        return taskList;
    }

    @Override
    public boolean createTask(Task task) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(TASK_COLLECTION);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String taskJson = objectMapper.writeValueAsString(task);
            mongoDbClient.putObject(taskJson);
            return true;
        } catch (JsonProcessingException e) {
            LOG.error("Couldn't serialize value into", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }
    }

}
