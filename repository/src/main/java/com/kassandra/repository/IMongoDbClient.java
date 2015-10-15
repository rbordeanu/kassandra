package com.kassandra.repository;

import java.util.Collection;
import java.util.Map;
import java.util.List;

public interface IMongoDbClient {
    boolean putObject(String json);

    String getObjectById(String _id);

    Collection<String> query(Map<String, String> attributes);
    
    List<String> getAll();
}
