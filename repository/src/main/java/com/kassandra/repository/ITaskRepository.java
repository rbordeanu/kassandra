package com.kassandra.repository;

import java.util.HashMap;
import java.util.List;

import com.kassandra.repository.model.Task;
import com.kassandra.repository.model.User;

public interface ITaskRepository {

    Task getTask(String id) throws RepositoryException;

    HashMap<String,String> getAll() throws RepositoryException;
    HashMap<String,String> getAvailableTasks(String userId) throws RepositoryException;

    boolean createTask(Task task) throws RepositoryException;
}
