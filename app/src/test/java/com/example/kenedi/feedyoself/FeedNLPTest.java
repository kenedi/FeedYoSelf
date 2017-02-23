package com.example.kenedi.feedyoself;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;


/**
 * Created by nicholasbradford on 2/22/17.
 */

public class FeedNLPTest {

    private static String emailSimpleYes = "Food tomorrow at 8pm at Salisbury Labs 103!";
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

//    @Test
//    public void extractLocation_simpleYes() {
//        String location = FeedNLP.extractLocation(emailSimpleYes);
//        assertNotNull(location);
//    }

    @Test
    public void extractLocation_simpleNo() {
        assertNull(FeedNLP.extractLocation(emailSimpleNo));
    }

//    @Test
//    public void extractDate_simpleYes() {
//        Date date = FeedNLP.extractDate(emailSimpleYes);
//        assertNotNull(date);
//    }

    @Test
    public void extractDate_simpleNo() {
        assertNull(FeedNLP.extractDate(emailSimpleNo));
    }

    @Test
    public void processEmail_simpleYes() {
        FoodEvent foodEvent = FeedNLP.processEmail(emailSimpleYes);
        assertNotNull(foodEvent);
    }

    @Test
    public void SUTimeDemoTest() {
        SUTimeDemo demo = new SUTimeDemo();
        String[] args = {emailSimpleYes, emailSimpleNo};
        demo.test(args);
    }
}
