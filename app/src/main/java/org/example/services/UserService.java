package org.example.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.Configuration.AuthUser;
import org.example.Configuration.DB;
import org.example.Configuration.sessions;
import org.example.entities.FoodRating;
import org.example.entities.Menu;
import org.example.entities.User;

import javax.security.auth.login.Configuration;
import java.awt.image.DataBuffer;
import java.io.ObjectInputFilter;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;

public class UserService {

//    List<User> users;

    private MongoCollection<Document> userCollection;
    private MongoCollection<Document> foodRatingCollection;
    private MongoCollection<Document> foodMenuCollection;

    public UserService(){
        MongoDatabase database= DB.getMongoClient().getDatabase("User_database");
        this.userCollection= database.getCollection("user");
        this.foodRatingCollection=database.getCollection("food rating");
        this.foodMenuCollection=database.getCollection("Menu");
    }
    

    public AuthUser authenitcateUser(Integer userID, String password){
        Document userDoc=userCollection.find(Filters.eq("userId",userID)).first();
        if(userDoc==null){
            System.out.println("User not found");
            return null;
        }
        String pass=userDoc.getString("password");
        if(pass!=null && pass.equals(password)){
            User CurrentUser=new User(userID,userDoc.getString("name"),userDoc.getString("role"));
            String sessionId=new sessions(userID).createSession();
            AuthUser authUser=new AuthUser(CurrentUser,sessionId);
            return authUser;
        }
        return null;
    }

    public boolean isValidSession(Integer userId,String currentSessionId){
        return new sessions(userId).userSessionValidation(currentSessionId);
    }



    public User createUser(String userName,String role,String password){
        User newuser=new User(userName,role,password);
        try {
            Document newUserData = new Document()
                    .append("userId", newuser.getUserId())
                    .append("name", newuser.getUserName())
                    .append("role", newuser.getRole())
                    .append("password", newuser.getPassword())
                    .append("hashedpassword", newuser.getHashedPassword())
                    .append("createdAt", newuser.getCreatedAt());
            userCollection.insertOne(newUserData);
            return newuser;
        } catch (Exception e) {
            return null;
        }
    }

    public  void  postRating(String menuId,String mealType,Integer userId,Integer rating,String comment){
        FoodRating foodRating=new FoodRating(userId,menuId,mealType,rating,comment);
        Document FRDATA=new Document()
                .append("Rating Id",foodRating.getRatingId())
                .append("UserId",userId)
                .append("MenuId",menuId)
                .append("Meal Type",mealType)
                .append("Rating",rating)
                .append("Comments",comment)
                .append("Created At",foodRating.getRatedAt());
        foodRatingCollection.insertOne(FRDATA);

    }

    public void viewMenu(String menuId){
        if(menuId.equalsIgnoreCase("All")){
            viewMenu("monday");
            viewMenu("tuesday");
            viewMenu("wednesday");
            viewMenu("thursday");
            viewMenu("friday");
            viewMenu("saturday");
            viewMenu("sunday");
        }
        Document menuDoc;
        try {
            menuDoc=foodMenuCollection.find(Filters.regex("MenuId", Pattern.compile("^"+menuId+"$",Pattern.CASE_INSENSITIVE))).first();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String Breakfast=menuDoc.getString("BreakFast");
        String Lunch=menuDoc.getString("Lunch");
        String Snack=menuDoc.getString("Snack");
        String Dinner=menuDoc.getString("Dinner");
        System.out.print("Menu day:"+menuId+"\nBreakfast:"+Breakfast+"\nLunch:"+Lunch);
        System.out.print("Evening Snack:"+Snack+"\nDinner:"+Dinner);
    }





    public void viewRatingOf(String menuId){
        List<Document> ratingsOfmenuId=foodRatingCollection.find(Filters.regex("MenuId",Pattern.compile("^"+menuId+"$",Pattern.CASE_INSENSITIVE))).into(new ArrayList<Document>());
        if(ratingsOfmenuId.isEmpty()){
            System.out.print("no ratings for "+menuId);
        }
        else{
            for(Document rating:ratingsOfmenuId){
                System.out.println("Ratings for menu: " + menuId);
                System.out.println("-UserId:"+rating.get("UserId")+"\nMeal Type:"+rating.getString("Meal Type")+"\nStars:"+rating.get("Rating")+"\nComment:"+rating.getString("Comments")+"\nPosted At:"+rating.getDate("Created At"));
                System.out.println("\n\n");
            }
        }
    }


    public void createMenu(String menuId,String breakfast,String lunch,String Snack,String Dinner){
        if(foodMenuCollection.find(Filters.regex("MenuId",Pattern.compile("^" +menuId+"$", Pattern.CASE_INSENSITIVE))).first()==null){
            Menu newMenu=new Menu(menuId,breakfast,lunch,Snack,Dinner);
            try{
                Document menuData=new Document()
                        .append("MenuId",menuId)
                        .append("BreakFast",breakfast)
                        .append("Lunch",lunch)
                        .append("Snack",Snack)
                        .append("Dinner",Dinner)
                        .append("Creation Date",newMenu.getDate());
                foodMenuCollection.insertOne(menuData);
                System.out.println("New Menu Added successfully");
//                return true;
            } catch (Exception e) {
                System.out.println("Error occurred in adding new Menu...pls retry");
//                return false;
            }
        }
        else {
            System.out.println("The menu already Exists...Go to menu Editor and Edit there..");
//            return false;
        }


    }




    public void EditMenu(String menuId,String mealType){
        if(mealType.equalsIgnoreCase("all")){
            EditMenu(menuId,"BreakFast");
            EditMenu(menuId,"Lunch");
            EditMenu(menuId,"Snack");
            EditMenu(menuId,"Dinner");
        }
        else{
            Document menuToEdit=foodMenuCollection.find(Filters.regex("MenuId",Pattern.compile("^"+menuId+"$",Pattern.CASE_INSENSITIVE))).first();
            Scanner scanner=new Scanner(System.in);
            if(menuToEdit!=null){
                Bson filter=Filters.eq("MenuId",menuId);
                System.out.println("Enter the new meal for:"+mealType);
                String newMeal=scanner.nextLine();
                Bson update= Updates.set(mealType,newMeal);
                foodMenuCollection.updateOne(filter,update);
                update= Updates.set("LastUpdate", Instant.now());
                foodMenuCollection.updateOne(filter,update);
            }
            else {
                System.out.println("No such Day in a week!!");
            }
        }

    }


    public void ViewMyRatings(Integer userId){
        List<Document> myRatings=foodRatingCollection.find(Filters.eq("UserId",userId)).into(new ArrayList<Document>());
        if(myRatings.isEmpty()){

        }
        else {
            for(Document rating:myRatings){
                System.out.println("Ratings By You: " + userId);
                System.out.println("-MenuId:"+rating.get("MenuId")+"\nMeal Type:"+rating.getString("Meal Type")+"\nStars:"+rating.get("Rating")+"\nComment:"+rating.getString("Comments")+"\nPosted At:"+rating.getDate("Created At"));
                System.out.println("\n\n");
            }
        }
    }

}


