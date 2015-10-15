package com.kassandra.repository.impl;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.repository.IMongoDbClient;
import com.kassandra.repository.IMongoDbProvider;
import com.kassandra.repository.ITaskRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.Task;
import com.kassandra.repository.model.User;
import org.slf4j.Logger;

public class TaskRepository implements ITaskRepository {

    private static final Logger LOG = getLogger(TaskRepository.class);
    private final String TASK_COLLECTION = "task";
    private final IMongoDbProvider mongoDbProvider;

    public TaskRepository(IMongoDbProvider mongoDbProvider) {
        this.mongoDbProvider = mongoDbProvider;
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
    public List<String> getAll() throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(TASK_COLLECTION);
        List<String> taskJson = mongoDbClient.getAll();
        if (taskJson.isEmpty()) {
            throw new RepositoryException(String.format("No document with type task found"));
        }
        return taskJson;
    }

    @Override
    public List<String> getAvailableTask(User user) throws RepositoryException {
        return null;
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
