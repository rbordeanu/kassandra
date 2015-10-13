package com.kassandra.repository;

import java.util.List;

public interface IMongoDbClient {
    boolean putObject(String json);

    String getObjectById(String query);

    List<String> getAll(String type);
}
