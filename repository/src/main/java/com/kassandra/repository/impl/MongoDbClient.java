package com.kassandra.repository.impl;

import static org.slf4j.LoggerFactory.getLogger;

import org.bson.Document;
import org.slf4j.Logger;

import com.kassandra.repository.IMongoDbClient;
import com.mongodb.client.MongoCollection;

public class MongoDbClient implements IMongoDbClient {
    private static final Logger LOG = getLogger(MongoDbClient.class);
    private final MongoCollection<Document> client;

    public MongoDbClient(MongoCollection<Document> realClient) {
        this.client = realClient;
    }

    public boolean putObject(String json) {
        Document doc = Document.parse(json);
        try {
            client.insertOne(doc);
            return true;
        } catch (Exception ex) {
            LOG.error("couldn't insert document {}", json, ex);
            return false;
        }
    }

    public String getObjectById(String id) {
        Iterable<Document> documents = client.find(new Document("_id", id));
        if (documents.iterator().hasNext()) {
            return documents.iterator().next().toJson();
        } else {
            return null;
        }
    }
}
