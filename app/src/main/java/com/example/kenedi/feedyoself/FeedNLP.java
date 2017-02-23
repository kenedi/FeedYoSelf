package com.example.kenedi.feedyoself;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Created by nicholasbradford on 2/22/17.
 */

public class FeedNLP {

    private static String[] FOOD_STRINGS = {
            "breakfast",
            "brunch",
            "dessert",
            "dinner",
            "drinks",
            "fare",
            "feast",
            "food",
            "lunch",
            "luncheon",
            "picnic",
            "pizza",
            "refreshment",
            "snack",
            "supper"
    };

    private static Set<String> FOOD_SET = new HashSet<>(Arrays.asList(FOOD_STRINGS));

    public static boolean isEmailAboutFood(String text) {
        String[] tokens = text.split(" ");
        List<String> textList = Arrays.asList(tokens);
        ListIterator<String> iterator = textList.listIterator();
        while (iterator.hasNext())
        {
            iterator.set(iterator.next().toLowerCase());
        }
        Set<String> textSet = new HashSet<>(textList);
        textSet.retainAll(FOOD_SET);
        return textSet.size() > 0;
    }

    public static String extractLocation(String text) {
        return null;
    }

    public static Date extractDate(String text) {
        return null;
    }


    public static FoodEvent processEmail(String text) {
        if (! isEmailAboutFood(text)) {
            return null;
        }
        String title = "TitlePlaceholder";
        String location = extractLocation(text);
        Date date = extractDate(text);
        return new FoodEvent(title, location, date);
    }
}
