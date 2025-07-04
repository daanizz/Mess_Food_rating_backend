/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import org.example.Configuration.AuthUser;
import org.example.entities.User;
import org.example.services.UserService;

import java.util.Scanner;

public class App {
    private static String currentSessionId=null;
    public static void main(String[] args) {
        Integer option=0;
        Scanner scanner=new Scanner(System.in);
        UserService userService=new UserService();
        while(option!=3){
            System.out.println("Welcome to Mess Committee connect Portal");
            System.out.println("1.Login");
            System.out.println("2.Create new account");
            System.out.println("3.Quite");
            option=scanner.nextInt();
            switch (option){
                case 1://LOGIN
                    System.out.println("Enter UserID");
                    Integer userID=scanner.nextInt();
                    System.out.println("Enter password");
                    String password=scanner.next();
                    AuthUser currentAuthUser = userService.authenitcateUser(userID, password);
                    User currentUser=currentAuthUser.getUser();
                    if(currentAuthUser==null){
                        System.out.println("Authentication failed!!");
                        break;
                    }
                    else{
                        currentSessionId=currentAuthUser.getUserSessionId();
                        System.out.println("Authentication successful :)");
                        System.out.println("Welcome "+currentUser.getUserName()+" to Mess_connect!");
                        if(currentUser.getRole().equalsIgnoreCase("student")){
                            Integer userOption=0;
                            while(userOption!=4){
                                if(!userService.isValidSession(userID,currentSessionId)){
                                    System.out.println("The Session got expired!!");
                                    break;
                                }

                                System.out.println("Options[select the Index to proceed]:");
                                System.out.println("1.Rate Food");
                                System.out.println("2.View Menu");
                                System.out.println("3.View My ratings");
                                System.out.println("4.Logout");
                                userOption= scanner.nextInt();
                                switch (userOption){
                                    case 1://rate
                                        System.out.println("Enter the menuId\nMonday\nTuesday\nwednesday\nThursday\nFriday\nSaturday\nSunday");
                                        String menuID;
                                        while(true){
                                            menuID=scanner.next();
                                            menuID=menuID.toLowerCase();
                                            if(menuID.equalsIgnoreCase("MOnday") || menuID.equalsIgnoreCase("tuesday") || menuID.equalsIgnoreCase("thursday") || menuID.equalsIgnoreCase("wednesday") || menuID.equalsIgnoreCase("friday") || menuID.equalsIgnoreCase("sunday")|| menuID.equalsIgnoreCase("saturday")){
                                                break;
                                            }
                                            else{
                                                System.out.println("Type exact menuId again..");
                                            }
                                        }

                                        System.out.println("Select the Meal type- type the meal:\nBreakfast\nLunch\nSnack\nDinner");
                                        String meal=scanner.next();
                                        System.out.println("Enter a rating between 1-5");
                                        Integer rating;
                                        while (true){
                                            rating=scanner.nextInt();
                                            if(rating>=1 && rating<=5){
                                                break;
                                            }
                                            else{
                                                System.out.println("Rating between 1 to 5 only allowed,please rate again");
                                            }
                                        }
                                        System.out.println("Any comments?");
                                        scanner.next();
                                        String comment=scanner.nextLine();
                                        userService.postRating(menuID,meal,userID,rating,comment);
                                        System.out.println("Rating Submitted successfully");
                                        break;
                                    case 2:
                                        System.out.println("Enter the day[monday,tuesday..etc] for which Menu should be viewed[enter \"All\" to view complete Menu]");
                                        String menuId=scanner.next();
                                        userService.viewMenu(menuId);
                                        break;
                                    case 3:
                                        userService.ViewMyRatings(userID);

                                }

                            }

                        } else if (currentUser.getRole().equalsIgnoreCase("Admin")) {
                            Integer userOption=0;
                            while(userOption!=7){
                                System.out.println("\n\n\nWelcome to Admin Portal...");
                                System.out.println("1.View ratings");
                                System.out.println("2.View menu");
                                System.out.println("3.Create menu");
                                System.out.println("4.Edit menu");
                                System.out.println("5.Delete menu");
                                System.out.println("6.Deleted/Archive menu");
                                System.out.println("7.Logout");
                                userOption=scanner.nextInt();
                                switch (userOption){
                                    case 1:
                                        System.out.println("\nEnter the day for which Menu should be viewed ");
                                        String ratingDay=scanner.next();
                                        userService.viewRatingOf(ratingDay);
                                        break;
                                    case 2:
                                        System.out.println("\nEnter the day[monday,tuesday..etc] for which Menu should be viewed[enter \"All\" to view complete Menu]");
                                        String menuId=scanner.next();
                                        userService.viewMenu(menuId);
                                        break;
                                    case 3:
                                        System.out.println("\nWelcome to Menu Creator");
                                        System.out.println("Select menuId[monday,tuesday...] to create");
                                        String menuDay=scanner.next();
                                        scanner.nextLine();
                                        System.out.println("Enter Breakfast food");
                                        String breakfast=scanner.nextLine();
                                        System.out.println("Enter Lunch food");
                                        String Lunch=scanner.nextLine();
                                        System.out.println("Enter Snack food");
                                        String Snack=scanner.nextLine();
                                        System.out.println("Enter Dinner food");
                                        String Dinner=scanner.nextLine();
                                        userService.createMenu(menuDay,breakfast,Lunch,Snack,Dinner);
                                        break;
                                    case 4://Menu Editor
                                        System.out.println("\nWelcome to Menu Editor");
                                        System.out.println("Select menuId[monday,tuesday...] to Edit");
                                        String editDay=scanner.next();
                                        scanner.nextLine();
                                        System.out.println("Select meal Type[breakfast,lunch,snack,dinner,All] to Edit");
                                        String mealType=scanner.next();
                                        scanner.nextLine();
                                        System.out.println("Choose Index\n1.Confirm\n2.Cancel");
                                        Integer CNFIndex=scanner.nextInt();
                                        if(CNFIndex==2){
                                            System.out.println("Change Cancelled");
                                            break;
                                        } else if (CNFIndex==1) {
                                            userService.EditMenu(editDay,mealType);
                                            System.out.println("Change Added");
                                            break;
                                        }
                                        else {
                                            System.out.println("Invalid Entry");
                                            break;
                                        }

//                                        scanner.nextLine();


//                                        break;
                                    case 5://Delete Menu
                                        break;


                                }
                            }

                        }

                    }
                    break;

                case 2:
                    System.out.println("\n\nCREATE NEW ACCOUNT:");

                    System.out.println("Select your role[Admin,Student]:");
                    String role=scanner.next();

                    System.out.println("Enter user name:");
                    String userName=scanner.next();

                    System.out.println("Enter Password:");
                    String Password=scanner.next();

                    System.out.println("Confirm Password:");

                    while(true){
                        String CNFPassword=scanner.next();
                        if(Password.equals(CNFPassword)){
                            break;
                        }
                        System.out.println("Confirmation password incorrect,pls Confirm password again..");
                    }

                    User createdUser=userService.createUser(userName,role,Password);
                    if(createdUser != null){
                        System.out.println("User creation successful");
                        System.out.println("Your user Id:"+createdUser.getUserId());
                    }
                    else {
                        System.out.println("User creation Error!!");
                    }
            }
        }
    }
}



