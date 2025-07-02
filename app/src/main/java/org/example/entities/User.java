package org.example.entities;

import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class User {
    private Integer userId;
    private String password;
    private String hashedPassword;
    private String userName;
    private String role;
    private LocalDate createdAt;
//    private LocalTime creationTime;  *******....Use redis Instead....*******
//    private LocalTime lastUpdateAt;

    public User(Integer userId,String password,String hashedPassword,String userName,String role,LocalDate createdAt){
        this.userId=userId;
        this.password=password;
        this.hashedPassword=hashedPassword;
        this.userName=userName;
        this.role=role;
        this.createdAt=createdAt;
    }

    public User(Integer userId,String userName,String role){
        this.userId=userId;
        this.userName=userName;
        this.role=role;
    }

    public User(String userName,String role,String password){
        this.userName=userName;
        this.role=role;
        this.password=password;
        this.hashedPassword= BCrypt.hashpw(password,BCrypt.gensalt());
        Random random=new Random();
        this.userId= 10000+random.nextInt(90000);
        this.createdAt=LocalDate.now() ;
    }

    public Integer getUserId(){
        return userId;
    }

    public String getPassword(){
        return password;
    }

    public String  getHashedPassword(){
        return hashedPassword;
    }

    public String getUserName(){
        return userName;
    }

    public String getRole(){
        return role;
    }

    public LocalDate getCreatedAt(){
        return createdAt;
    }


    public void setUserId(Integer userId){
        this.userId=userId;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
