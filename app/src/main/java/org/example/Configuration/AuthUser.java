package org.example.Configuration;

import org.example.entities.User;

public class AuthUser {
    private User user;
    private String userSessionId;

    public AuthUser(User user,String userSessionId){
        this.user=user;
        this.userSessionId=userSessionId;
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    public User getUser() {
        return user;
    }
}
