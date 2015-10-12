package com.kassandra.repository.impl;

import com.kassandra.repository.IMongoDbClient;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Created by aAlex on 10/8/2015.
 */
public class MongoDbClient implements IMongoDbClient {
    private final MongoCollection<Document> client;
    public MongoDbClient(MongoCollection<Document>  realClient){
        this.client= realClient;
    }
}
