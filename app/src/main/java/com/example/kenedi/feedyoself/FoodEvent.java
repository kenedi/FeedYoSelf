package com.example.kenedi.feedyoself;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nicholasbradford on 2/22/17.
 */

public class FoodEvent implements Serializable{

    String tile;
    String location;
    Date date;

    public FoodEvent(String title) {
        this(title, null);
    }

    public FoodEvent(String title, String location) {
        this(title, location, null);
    }

    public FoodEvent(String title, String location, Date date) {
        this.tile = title;
        this.location = location;
        this.date = date;
    }

    public String getTitle(){
        return this.tile;
    }

    public  String getLoc(){
        return  this.location;
    }

    public String prettyPrint(){
        return getTitle() + " " + getLoc() + " " + date.toString();
    }
}
