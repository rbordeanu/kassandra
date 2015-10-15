package com.kassandra.repository;

import java.util.List;

import com.kassandra.repository.model.Task;
import com.kassandra.repository.model.User;

public interface ITaskRepository {

    Task getTask(String id) throws RepositoryException;

    List<String> getAll() throws RepositoryException;
    List<String> getAvailableTask(User user) throws RepositoryException;

    boolean createTask(Task task) throws RepositoryException;
}
