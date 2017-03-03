package com.example.kenedi.feedyoself;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nicholasbradford on 2/22/17.
 */

public class FoodEvent implements Serializable, Comparable<FoodEvent>{

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

    public Date getDate() { return this.date; }

//    public String prettyPrint(){
//        return getTitle() + " " + getLoc() + " " + date.toString();
//    }

    @Override
    public int compareTo(FoodEvent o) {
        int answer = 0;
        if (this.date == null && o.date == null){
            answer = 0;
        }
        else if (this.date == null) {
            answer = 1;
        }
        else if (o.date == null) {
            answer = -1;
        }
        else {
            int thisTime = this.date.getHours() * 60 + this.date.getMinutes();
            int otherTime = o.date.getHours() * 60 + o.date.getMinutes();
            answer = otherTime - thisTime; // assumes no overflow
        }
        return answer;
    }
}
