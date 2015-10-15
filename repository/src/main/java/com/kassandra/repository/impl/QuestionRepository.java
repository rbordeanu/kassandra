package com.kassandra.repository.impl;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.repository.*;
import com.kassandra.repository.model.Question;
import org.slf4j.Logger;


public class QuestionRepository implements IQuestionRepository {

    private static final Logger LOG = getLogger(QuestionRepository.class);
    private final String Q_COLLECTION = "task";
    private final IMongoDbProvider mongoDbProvider;

    public QuestionRepository(IMongoDbProvider mongoDbProvider) {
        this.mongoDbProvider = mongoDbProvider;
    }

    @Override
    public IQuestion getQuestion(String _id) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(Q_COLLECTION);
        String questionJson = mongoDbClient.getObjectById(_id);
        if (questionJson == null) {
            throw new RepositoryException(String.format("No document with id %s found", _id));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(questionJson, Question.class);
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }
    }

    @Override
    public List<String> getAll(String type) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(Q_COLLECTION);
        List<String> questionJson = mongoDbClient.getAll(type);
        if (questionJson.isEmpty()) {
            throw new RepositoryException(String.format("No document with type %s found", type));
        }
        return questionJson;

    }

    @Override
    public boolean createQuestion(IQuestion question) throws RepositoryException {
        IMongoDbClient mongoDbClient = mongoDbProvider.create(Q_COLLECTION);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonQuestion = objectMapper.writeValueAsString(question);
            mongoDbClient.putObject(jsonQuestion);
            return true;
        } catch (JsonProcessingException e) {
            LOG.error("Couldn't serialize value into", e);
            throw new RepositoryException("Couldn't deserialize from json.");
        }
    }
}
