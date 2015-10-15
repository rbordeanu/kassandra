package com.kassandra.repository;

import java.util.Collection;
import java.util.Map;

public interface IMongoDbClient {
    boolean putObject(String json);

    String getObjectById(String _id);

    Collection<String> query(Map<String, String> attributes);
}
