package org.example.entities;

//        "ratingId": "R001",
//        "userId": "U001", // Who rated?
//        "menuId": "M001", // Which meal?
//        "mealType": "lunch", // breakfast/lunch/dinner
//        "rating": 4, // 1-5 scale
//        "ratedAt": "2025-06-17T13:30:00Z"
//        "comment": "The chicken was spicy!",

import java.sql.Time;
import java.time.Instant;
import java.util.UUID;

public class FoodRating {

    private final String ratingId;
    private final Integer userId;
    private final String menuId;
    private String mealType;
    private Integer rating;
    private final Instant ratedAt;
    private String comment;

    public FoodRating(Integer userId,String menuId,String mealType,Integer rating,String comment){
//        if(userId==null || menuId==null || mealType==null || (rating==null && comment==null)){
//            throw new IllegalArgumentException("The field cant be null");
//        }
//        if(rating<1 || rating>5){
//            throw new IllegalArgumentException("Rating should be between 1-5");
//        }
        this.userId=userId;
        this.menuId=menuId;
        this.mealType=mealType;
        this.rating=rating;
        this.comment=comment;
        ratingId= UUID.randomUUID().toString();
        ratedAt= Instant.now();
    }

    public Integer getUserId() {
        return userId;
    }


    public Instant getRatedAt() {
        return ratedAt;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getRatingId() {
        return ratingId;
    }

    public String getMealType() {
        return mealType;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
