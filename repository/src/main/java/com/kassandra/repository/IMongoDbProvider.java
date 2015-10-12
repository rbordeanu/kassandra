package com.kassandra.repository;

public interface IMongoDbProvider {
    IMongoDbClient create(String collection);
}
