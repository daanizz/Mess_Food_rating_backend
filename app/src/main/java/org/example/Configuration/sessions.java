package org.example.Configuration;

import com.mongodb.internal.connection.Time;
import redis.clients.jedis.Jedis;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Map;

public class sessions {
    Integer userId;
    private static final int Session_time=30;
    private static final String Session_prefix=".session";

    public sessions(Integer userId){
        this.userId=userId;
    }

    public Integer getUserId() {
        return userId;
    }

//    public void updateLastAccess() {
//        this.lastAccess =LocalTime.now() ;
//    }

//    public void inValidate(){
//        this.isValid=false;
//    }


    public  String createSession(){
        try(Jedis jedis=RedisConfig.getResources()) {
            String sessionId=Session_prefix+userId+":"+ Instant.now().toEpochMilli();

            jedis.hset(sessionId,
                    Map.of(
                            "userId",userId.toString(),
                            "LastAccess",Instant.now().toString()
                    )
            );

            jedis.expire(sessionId,Session_time*60);
            return sessionId;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean userSessionValidation(String sessionId){
        try (Jedis jedis=RedisConfig.getResources()){
            if(!jedis.exists(sessionId)){
                return  false;
            }

            jedis.hset(sessionId,"lastAccess",Instant.now().toString());
            jedis.expire(sessionId,Session_time*60);
            return true;
        }
    }
}
