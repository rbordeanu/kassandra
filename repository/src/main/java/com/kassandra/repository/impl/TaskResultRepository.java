package com.kassandra.repository.impl;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.repository.IMongoDbClient;
import com.kassandra.repository.IMongoDbProvider;
import com.kassandra.repository.ITaskResultRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.TaskResult;
import org.slf4j.Logger;

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
    public List<TaskResult> getAllByUser(String userId) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(TASK_RESULT_COLLECTION);
        List<String> taskListJson = mongoDbClient.getAll();

        List<TaskResult> resultList = new ArrayList<TaskResult>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            for(String taskJson  : taskListJson) {
                TaskResult taskResult =  objectMapper.readValue(taskJson, TaskResult.class);
                if(userId.equals(taskResult.getUserId())){
                    resultList.add(taskResult);
                }
            }
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }

        return resultList;
    }

    @Override
    public List<TaskResult> getAllByTask(String taskId) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(TASK_RESULT_COLLECTION);
        List<String> taskListJson = mongoDbClient.getAll();

        List<TaskResult> resultList = new ArrayList<TaskResult>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            for(String taskJson  : taskListJson) {
                TaskResult taskResult =  objectMapper.readValue(taskJson, TaskResult.class);
                if(taskId.equals(taskResult.getTaskId())){
                    resultList.add(taskResult);
                }
            }
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }


        return resultList;
    }

    @Override
    public boolean createTaskResult(TaskResult task) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(TASK_RESULT_COLLECTION);
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
