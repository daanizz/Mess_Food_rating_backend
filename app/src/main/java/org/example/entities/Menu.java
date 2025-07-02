package org.example.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Menu {

    private final String menuId;
    private String breakfast;
    private String lunch;
    private String eveninSnack;
    private String dinner;
//    private Boolean isHoliday;
    private final LocalDate date;


    public Menu(String menuId,String breakfast,String lunch,String eveninSnack,String dinner){
        this.menuId=menuId;
        this.breakfast=breakfast;
        this.lunch=lunch;
        this.eveninSnack=eveninSnack;
        this.dinner=dinner;
        this.date=LocalDate.now();
    }


    public String getMenuId() {
        return menuId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getBreakfast() {
        return breakfast;
    }


    public String getDinner() {
        return dinner;
    }

    public String getEveninSnack() {
        return eveninSnack;
    }

    public String getLunch() {
        return lunch;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }


    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public void setEveninSnack(String eveninSnack) {
        this.eveninSnack = eveninSnack;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }
}
