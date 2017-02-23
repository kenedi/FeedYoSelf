package com.example.kenedi.feedyoself;

import java.util.Date;

/**
 * Created by nicholasbradford on 2/22/17.
 */

public class FoodEvent {

    public FoodEvent(String title) {
        this(title, null);
    }

    public FoodEvent(String title, String location) {
        this(title, location, null);
    }

    public FoodEvent(String title, String location, Date date) {

    }
}
