package com.example.kenedi.feedyoself;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.POSTaggerAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.pipeline.WordsToSentencesAnnotator;
import edu.stanford.nlp.time.SUTime;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.time.TimeExpression;
import edu.stanford.nlp.util.CoreMap;

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

    static FoodEvent processEmail(String text) {
        if (! isEmailAboutFood(text)) {
            return null;
        }
        String title = "TitlePlaceholder";
        String location = extractLocation(text);
        Date date = extractDate(text);
        return new FoodEvent(title, location, date);
    }

    static boolean isEmailAboutFood(String text) {
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

    // LOCATION

    private static void handleEntity(String inKey, StringBuilder inSb, List inTokens) {
        System.out.println(inSb + " is a " + inKey);
//        inTokens.add(new EmbeddedToken(inKey, inSb.toString()));
//        inSb.setLength(0);
    }

    /**
     * Inspired by http://www.informit.com/articles/article.aspx?p=2265404
     * @param text
     * @return
     */
    static String extractLocation(String text) {
        List tokens = new ArrayList<>();

        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and
        Properties props = new Properties();
        boolean useRegexner = false;
        if (useRegexner) {
            props.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner");
            props.put("regexner.mapping", "locations.txt");
        } else {
            props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
        }
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);


        // run all Annotators on the passed-in text
        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with
        // custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuilder sb = new StringBuilder();

        //I don't know why I can't get this code out of the box from StanfordNLP, multi-token entities
        //are far more interesting and useful..
        //TODO make this code simpler..
        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence, "O" is a sensible default to initialise
            // tokens to since we're not interested in unclassified / unknown things..
            String prevNeToken = "O";
            String currNeToken = "O";
            boolean newToken = true;
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                currNeToken = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // Strip out "O"s completely, makes code below easier to understand
                if (currNeToken.equals("O")) {
                    // LOG.debug("Skipping '{}' classified as {}", word, currNeToken);
                    if (!prevNeToken.equals("O") && (sb.length() > 0)) {
                        handleEntity(prevNeToken, sb, tokens);
                        newToken = true;
                    }
                    continue;
                }

                if (newToken) {
                    prevNeToken = currNeToken;
                    newToken = false;
                    sb.append(word);
                    continue;
                }

                if (currNeToken.equals(prevNeToken)) {
                    sb.append(" " + word);
                } else {
                    // We're done with the current entity - print it out and reset
                    // TODO save this token into an appropriate ADT to return for useful processing..
                    handleEntity(prevNeToken, sb, tokens);
                    newToken = true;
                }
                prevNeToken = currNeToken;
            }
        }
        return null;
    }

    // DATES

    private static Date getDates(List<CoreMap> timexAnnsAll) {
        if (timexAnnsAll.size() == 0){
            return null;
        }

        CoreMap cm = timexAnnsAll.get(0);
        SUTime.Temporal temporal = cm.get(TimeExpression.Annotation.class).getTemporal();
        String originalString = temporal.toString();
        StringBuilder sb = new StringBuilder(originalString);
        sb.deleteCharAt(sb.indexOf("T"));
        String modifiedString = sb.toString();
        System.out.println("Modified string: " + modifiedString);

        // convert to Date object
        DateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm"); // 2013-07-1520:00
        Date startDate = null;
        try {
            startDate = df.parse(modifiedString);
            String newDateString = df.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    private static void prettyPrint(Annotation annotation, List<CoreMap> timexAnnsAll) {
        System.out.println(annotation.get(CoreAnnotations.TextAnnotation.class));
        for (CoreMap cm : timexAnnsAll) {
            List<CoreLabel> tokens = cm.get(CoreAnnotations.TokensAnnotation.class);
            System.out.println(cm + " [from char offset " +
                    tokens.get(0).get(CoreAnnotations.CharacterOffsetBeginAnnotation.class) +
                    " to " + tokens.get(tokens.size() - 1).get(CoreAnnotations.CharacterOffsetEndAnnotation.class) + ']' +
                    " --> " + cm.get(TimeExpression.Annotation.class).getTemporal());
        }
        System.out.println("--");
    }

    /**
     * Inspired by http://nlp.stanford.edu/software/sutime.html
     * @param text
     * @return
     */
    static Date extractDate(String text) {
        Properties props = new Properties();
        AnnotationPipeline pipeline = new AnnotationPipeline();
        pipeline.addAnnotator(new TokenizerAnnotator(false));
        pipeline.addAnnotator(new WordsToSentencesAnnotator(false));
        pipeline.addAnnotator(new POSTaggerAnnotator(false));
        pipeline.addAnnotator(new TimeAnnotator("sutime", props));

        Annotation annotation = new Annotation(text);
        annotation.set(CoreAnnotations.DocDateAnnotation.class, "2013-07-14");
        pipeline.annotate(annotation);
        List<CoreMap> timexAnnsAll = annotation.get(TimeAnnotations.TimexAnnotations.class);

        prettyPrint(annotation, timexAnnsAll);
        return getDates(timexAnnsAll);
    }
}
