package com.kassandra.repository;

import java.util.HashMap;
import java.util.List;

import com.kassandra.repository.model.Task;

public interface ITaskResultRepository {

    List<String> getAll() throws RepositoryException;

    HashMap<String,String> getAllByUser(String userId) throws RepositoryException;

    HashMap<String,String> getAllByTask(String taskId) throws RepositoryException;

    boolean createTask(Task task) throws RepositoryException;

}
