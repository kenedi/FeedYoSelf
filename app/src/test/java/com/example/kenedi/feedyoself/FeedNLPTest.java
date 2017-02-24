package com.example.kenedi.feedyoself;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;


/**
 * Created by nicholasbradford on 2/22/17.
 */

public class FeedNLPTest {

    private static String emailSimpleYes = "Food tomorrow at 8pm in Salisbury Labs 103!";
    private static String emailSimpleNo = "Remember to get that homework assignment complete.";

    @Test
    public void isNotNull() {
        FeedNLP nlp = new FeedNLP();
        assertNotNull(nlp);
    }

    @Test
    public void isEmailAboutFood_simpleYes() {
        assertTrue(FeedNLP.isEmailAboutFood(emailSimpleYes));
    }

    @Test
    public void isEmailAboutFood_simpleNo() {
        assertFalse(FeedNLP.isEmailAboutFood(emailSimpleNo));
    }

    @Test
    public void extractLocation_simpleYes() {
        String answer = "Salisbury Labs";
        String location = FeedNLP.extractLocation(emailSimpleYes);
        assertNotNull(location);
        assertEquals(location, answer);
    }

    @Test
    public void extractLocation_simpleNo() {
        assertNull(FeedNLP.extractLocation(emailSimpleNo));
    }

    @Test
    public void test_getTodaysDateString() {
        System.out.println(FeedNLP.getTodaysDateString());
    }

    @Test
    public void extractDate_simpleYes() {
        String dateString = FeedNLP.getTodaysDateString();
        Date trueDate = null;
        try {
            trueDate = (new SimpleDateFormat("yyyy-MM-ddHH:mm")).parse(dateString + "20:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        trueDate.setDate(trueDate.getDate() + 1);
        Date date = FeedNLP.extractDate(emailSimpleYes);
        assertNotNull(date);
        assertTrue(date.equals(trueDate));
    }

    @Test
    public void extractDate_simpleNo() {
        assertNull(FeedNLP.extractDate(emailSimpleNo));
    }

    @Test
    public void processEmail_simpleYes() {
        FoodEvent foodEvent = FeedNLP.processEmail(emailSimpleYes);
        assertNotNull(foodEvent);
    }

}
