package com.kassandra.repository;

import com.kassandra.repository.model.User;

public interface IUserRepository {
    User getUser(String id) throws RepositoryException;

    boolean createUser(User user) throws RepositoryException;
}
