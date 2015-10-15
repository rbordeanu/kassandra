package com.kassandra.repository.impl;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.repository.IMongoDbClient;
import com.kassandra.repository.IMongoDbProvider;
import com.kassandra.repository.ITaskResultRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.Task;
import com.kassandra.repository.model.TaskResult;
import com.kassandra.repository.model.User;

public class TaskResultRepository implements ITaskResultRepository {

    private static final Logger LOG = getLogger(TaskRepository.class);
    private final String TASK_RESULT_COLLECTION = "taskResult";
    private final IMongoDbProvider mongoDbProvider;

    public TaskResultRepository(IMongoDbProvider mongoDbProvider) {
        this.mongoDbProvider = mongoDbProvider;
    }

    @Override
    public List<String> getAll() throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(TASK_RESULT_COLLECTION);
        List<String> taskListJson = mongoDbClient.getAll();
        List<String> listResultTasks = new ArrayList();
        if (taskListJson.isEmpty()) {
            throw new RepositoryException(String.format("No document with type task found"));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            for(String taskJson  : taskListJson) {
                TaskResult taskResult =  objectMapper.readValue(taskJson, TaskResult.class);
                listResultTasks.add(taskResult.get_id());
            }
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }

        return listResultTasks;
    }

    @Override
    public HashMap<String,String> getAllByUser(String userId) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(TASK_RESULT_COLLECTION);
        List<String> taskListJson = mongoDbClient.getAll();

        HashMap<String,String> map = new HashMap();
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            for(String taskJson  : taskListJson) {
                TaskResult taskResult =  objectMapper.readValue(taskJson, TaskResult.class);
                if(userId.equals(taskResult.getUserId()))
                map.put(taskResult.get_id(), taskResult.getTaskId());
            }
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }


        return map;
    }

    @Override
    public HashMap<String,String> getAllByTask(String taskId) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(TASK_RESULT_COLLECTION);
        List<String> taskListJson = mongoDbClient.getAll();

        HashMap<String,String> map = new HashMap();
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            for(String taskJson  : taskListJson) {
                TaskResult taskResult =  objectMapper.readValue(taskJson, TaskResult.class);
                if(taskId.equals(taskResult.getTaskId()))
                    map.put(taskResult.get_id(), taskResult.getUserId());
            }
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }


        return map;
    }

    @Override
    public boolean createTask(Task task) throws RepositoryException {
        return false;
    }
}
