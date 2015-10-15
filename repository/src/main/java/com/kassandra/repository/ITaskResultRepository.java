package com.kassandra.repository;

import java.util.List;

import com.kassandra.repository.model.TaskResult;

public interface ITaskResultRepository {

    List<String> getAll() throws RepositoryException;

    List<TaskResult> getAllByUser(String userId) throws RepositoryException;

    List<TaskResult> getAllByTask(String taskId) throws RepositoryException;

    boolean createTaskResult(TaskResult task) throws RepositoryException;

}
