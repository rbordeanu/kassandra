package com.kassandra.repository;

import java.util.List;

import com.kassandra.repository.model.User;

public interface IUserRepository {
    User getUser(String id) throws RepositoryException;

    boolean createUser(User user) throws RepositoryException;

    String validateLogin(String username, String password) throws RepositoryException;

    List<User> getAll() throws RepositoryException;
}
