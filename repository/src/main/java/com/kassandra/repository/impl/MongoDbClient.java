package com.kassandra.repository.impl;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;

import com.kassandra.repository.IMongoDbClient;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
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
        FindIterable<Document> documents = client.find(new Document("_id", id));
        if (documents.iterator().hasNext()) {
            return documents.iterator().next().toJson();
        } else {
            return null;
        }
    }

    public boolean exists(String singleAttributeName, String singleAttributeValue) {
        final long count = client.count(new Document(singleAttributeName, singleAttributeValue));
        if (count == 0) {
            return false;
        }
        return true;
    }

    public Collection<String> query(Map<String, String> attributes) {
        final Collection<String> results = new ArrayList<String>();
        boolean isFirst = true;
        Document query = null;
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if (isFirst) {
                query = new Document(entry.getKey(), entry.getValue());
                isFirst = false;
            } else {
                query.append(entry.getKey(), entry.getValue());
            }
        }
        FindIterable<Document> documents = client.find(query);
        documents.forEach(new Block<Document>() {
            public void apply(Document document) {
                results.add(document.toJson());
            }
        });
        return results;
    }

    public List<String> getAll() {
        final List<String> ids = new ArrayList();
        FindIterable<Document> documents = client.find();

        documents.forEach(new Block<Document>() {
            public void apply(Document document) {
                ids.add(document.toJson());
            }
        });
        return ids;
    }


}
