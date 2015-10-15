package com.kassandra.repository.impl;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.repository.IMongoDbClient;
import com.kassandra.repository.IMongoDbProvider;
import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.User;

public class UserRepository implements IUserRepository {
    private static final Logger LOG = getLogger(UserRepository.class);
    private final String USER_COLLECTION = "user";
    private final IMongoDbProvider mongoDbProvider;

    public UserRepository(IMongoDbProvider mongoDbProvider) {
        this.mongoDbProvider = mongoDbProvider;
    }

    public User getUser(String _id) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(USER_COLLECTION);
        String userJson = mongoDbClient.getObjectById(_id);
        if (userJson == null) {
            throw new RepositoryException(String.format("No document with id %s found", _id));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(userJson, User.class);
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }
    }

    public boolean createUser(User user) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(USER_COLLECTION);
        // validate first to check we don't have any other user registered with same email or
        // username
        if (!mongoDbClient.exists("email", user.getEmail())
                && !mongoDbClient.exists("aturbatu", user.getUsername())) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String jsonUser = objectMapper.writeValueAsString(user);
                mongoDbClient.putObject(jsonUser);
                return true;
            } catch (JsonProcessingException e) {
                LOG.error("Couldn't serialize value into", e);
                throw new RepositoryException("Couldn't deserialize from json.");
            }
        }
        throw new RepositoryException("User already exists.");
    }

    public boolean validateLogin(String email, String password) {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(USER_COLLECTION);
        Map<String, String> map = new HashMap<String, String>();
        map.put("email", email);
        map.put("password", password);

        if (1 == mongoDbClient.query(map).size()) {
            return true;
        }
        return false;
    }
}
