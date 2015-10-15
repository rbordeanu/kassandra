package com.kassandra.repository;

public interface IMongoDbClient {
    boolean putObject(String json);

    String getObjectById(String query);
}
