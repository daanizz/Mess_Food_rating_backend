package org.example.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class DB {
    private static final MongoClient mongoClient;

    static {
        mongoClient= MongoClients.create("mongodb://localhost:27017");
    }

    public static MongoClient getMongoClient(){
        return mongoClient;
    }

    public static void closeConnection(){
        if(mongoClient!=null){
            mongoClient.close();
        }
    }
}
