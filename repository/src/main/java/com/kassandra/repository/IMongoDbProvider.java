package com.kassandra.repository;

/**
 * Created by aAlex on 10/8/2015.
 */
public interface IMongoDbProvider {
    IMongoDbClient create(String collection);
}
