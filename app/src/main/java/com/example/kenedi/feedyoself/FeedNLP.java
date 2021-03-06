package com.example.kenedi.feedyoself;

//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.ListIterator;
//import java.util.Properties;
//import java.util.Set;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// old build.gradle:
//apply plugin: 'com.android.application'
//
//        android {
//        compileSdkVersion 25
//        buildToolsVersion "25.0.2"
//        defaultConfig {
//        applicationId "com.example.kenedi.feedyoself"
//        minSdkVersion 18
//        targetSdkVersion 25
//        versionCode 1
//        versionName "1.0"
//        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
//        multiDexEnabled true
//        jackOptions {
//        enabled true
//        }
//        }
//        buildTypes {
//        release {
//        minifyEnabled false
//        proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
//        }
//        }
//        compileOptions {
//        sourceCompatibility JavaVersion.VERSION_1_8
//        targetCompatibility JavaVersion.VERSION_1_8
//        }
//        packagingOptions {
//        exclude 'edu/stanford/nlp/pipeline/demo/**'
//        }
//        }
//
//        repositories {
//        maven {
//        url 'http://oss.jfrog.org/artifactory/oss-snapshot-local'
//        }
//        }
//
//        dependencies {
//        compile fileTree(dir: 'libs', include: ['*.jar'])
//        androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
//        exclude group: 'com.android.support', module: 'support-annotations'
//        })
//        compile 'com.android.support:appcompat-v7:25.1.1'
//        compile 'com.google.android.gms:play-services:10.0.1'
//        compile 'com.google.android.gms:play-services-auth:10.0.1'
//        testCompile 'junit:junit:4.12'
//        compile 'edu.stanford.nlp:stanford-corenlp:3.7.0'
//        compile 'edu.stanford.nlp:stanford-corenlp:3.7.0:models'
//        compile 'pub.devrel:easypermissions:0.2.1'
//        compile('com.google.api-client:google-api-client-android:1.22.0') {
//        exclude group: 'org.apache.httpcomponents'
//        }
//        compile('com.google.apis:google-api-services-gmail:v1-rev61-1.22.0') {
//        exclude group: 'org.apache.httpcomponents'
//        }
//        compile('com.google.code.findbugs:jsr305:2.0.1')
//
//        }


// in app/build.gradle:
//  compile 'edu.stanford.nlp:stanford-corenlp:3.7.0'
//  compile 'edu.stanford.nlp:stanford-corenlp:3.7.0:models'
//import edu.stanford.nlp.ling.CoreAnnotations;
//import edu.stanford.nlp.ling.CoreLabel;
//import edu.stanford.nlp.pipeline.Annotation;
//import edu.stanford.nlp.pipeline.AnnotationPipeline;
//import edu.stanford.nlp.pipeline.POSTaggerAnnotator;
//import edu.stanford.nlp.pipeline.StanfordCoreNLP;
//import edu.stanford.nlp.pipeline.TokenizerAnnotator;
//import edu.stanford.nlp.pipeline.WordsToSentencesAnnotator;
//import edu.stanford.nlp.time.SUTime;
//import edu.stanford.nlp.time.TimeAnnotations;
//import edu.stanford.nlp.time.TimeAnnotator;
//import edu.stanford.nlp.time.TimeExpression;
//import edu.stanford.nlp.util.CoreMap;
//import edu.stanford.nlp.util.StringUtils;

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
            "supper",
            "donut",
            "coffee"
    };

    private static String[] LOCATION_STRINGS = {
            "Atwater Kent",
//            "AK",
            "Fuller Laboratories",
            "Fuller Labs",
            "Fuller Lab",
//            "FL",
            "Goddard Hall",
//            "GH",
            "Higgins Laboratories",
            "Higgins Labs",
            "Higgins Lab",
//            "HL",
            "Kaven Hall",
//            "KH",
            "Olin Hall",
//            "OH",
            "Campus Center",
//            "CC",
            "Salisbury Laboratories",
            "Salisbury Labs",
            "Salisbury Lab"
    };

    private static String[] TIME_FORMAT_STRINGS = {
            "hh:mm:ss",
            "hh:mm:ss",
            "hh:mm",
            "h:mm",
            "hh",
            "h"
    };

    //    private static Set<String> FOOD_SET = new HashSet<>(Arrays.asList(FOOD_STRINGS));
    private static Pattern FOOD_PATTERN = assemblePattern(FOOD_STRINGS, false);
    private static Pattern LOCATION_PATTERN = assemblePattern(LOCATION_STRINGS, true);
    private static List<String> TIME_FORMAT_LIST = Arrays.asList(TIME_FORMAT_STRINGS);


    static FoodEvent processEmail(String text) {
        if (!isEmailAboutFood(text)) {
            return null;
        }
        return new FoodEvent(extractTitle(text), extractLocation(text), extractTime(text));
    }

    static boolean isEmailAboutFood(String text) {
//        String[] tokens = text.split(" ");
//        List<String> textList = Arrays.asList(tokens);
//        ListIterator<String> iterator = textList.listIterator();
//        while (iterator.hasNext())
//        {
//            iterator.set(iterator.next().toLowerCase());
//        }
//        Set<String> textSet = new HashSet<>(textList);
//        textSet.retainAll(FOOD_SET);
//        return textSet.size() > 0;
        String food = getFirstStringMatchingPattern(FOOD_PATTERN, text);
        return null != food;
    }

    private static String getFirstStringMatchingPattern(Pattern pattern, String text) {
        Matcher m = pattern.matcher(text);
        String answer = null;
        if (m.find()) {
            answer = m.group(1);
        }
        return answer;
    }

    private static Pattern assemblePattern(String[] words, boolean isNumberAllowed) {
        List potentialWords = Arrays.asList(words);
        String lstr = potentialWords.toString();
        String regex = "(" + lstr.substring(1, lstr.length() - 1).replace(", ", "|") + ")";
//        if (isNumberAllowed) {
//            regex += "|" + lstr.substring(1, lstr.length() - 1).replace(", ", " \\+?\\d+|") + " \\+?\\d+";
//        }

        Pattern locationPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return locationPattern;
    }

    static String extractTitle(String text) {
        return getFirstStringMatchingPattern(FOOD_PATTERN, text);
    }

    // LOCATION

    private static void handleEntity(String inKey, StringBuilder inSb, List inTokens) {
        System.out.println(inSb + " is a " + inKey);
//        inTokens.add(new EmbeddedToken(inKey, inSb.toString()));
//        inSb.setLength(0);
    }

    /**
     * @param text
     * @return
     */
    static String extractLocation(String text) {
//        // Inspired by http://www.informit.com/articles/article.aspx?p=2265404
//        Properties props = new Properties();
//        props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
//        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//        Annotation document = new Annotation(text);
//        pipeline.annotate(document);
//        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
//        List<String> answer = new ArrayList<>();
//        for (CoreMap sentence : sentences) {
//            String currNeToken;
//            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
//                currNeToken = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
//                String word = token.get(CoreAnnotations.TextAnnotation.class);
//                System.out.println("Token: " + word + " is a " + currNeToken);
//                if (currNeToken.equals("LOCATION")){
//                    answer.add(word);
//                }
//            }
//        }
//        return answer.size() > 1 ? StringUtils.join(answer, " ") : null;
        return getFirstStringMatchingPattern(LOCATION_PATTERN, text);
    }

    // DATES

//    private static Date getDates(List<CoreMap> timexAnnsAll) {
//        if (timexAnnsAll.size() == 0){
//            return null;
//        }
//
//        CoreMap cm = timexAnnsAll.get(0);
//        SUTime.Temporal temporal = cm.get(TimeExpression.Annotation.class).getTemporal();
//        String originalString = temporal.toString();
//        StringBuilder sb = new StringBuilder(originalString);
//        sb.deleteCharAt(sb.indexOf("T"));
//        String modifiedString = sb.toString();
//        System.out.println("Modified string: " + modifiedString);
//
//        // convert to Date object
//        DateFormat df = new SimpleDateFormat("yyyy-MM-ddHH:mm"); // 2013-07-1520:00
//        Date startDate = null;
//        try {
//            startDate = df.parse(modifiedString);
//            String newDateString = df.format(startDate);
//            System.out.println(newDateString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return startDate;
//    }

//    private static void prettyPrint(Annotation annotation, List<CoreMap> timexAnnsAll) {
//        System.out.println(annotation.get(CoreAnnotations.TextAnnotation.class));
//        for (CoreMap cm : timexAnnsAll) {
//            List<CoreLabel> tokens = cm.get(CoreAnnotations.TokensAnnotation.class);
//            System.out.println(cm + " [from char offset " +
//                    tokens.get(0).get(CoreAnnotations.CharacterOffsetBeginAnnotation.class) +
//                    " to " + tokens.get(tokens.size() - 1).get(CoreAnnotations.CharacterOffsetEndAnnotation.class) + ']' +
//                    " --> " + cm.get(TimeExpression.Annotation.class).getTemporal());
//        }
//        System.out.println("--");
//    }

//    private static String prependZeroIfNecessary(String s) {
//        return s.length() > 1 ? s : "0" + s;
//    }
//
//    static String getTodaysDateString() {
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        String year = Integer.toString(cal.get(Calendar.YEAR));
//        String month = Integer.toString(cal.get(Calendar.MONTH) + 1); // 0-indexed for some reason
//        String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
//        month = prependZeroIfNecessary(month);
//        day = prependZeroIfNecessary(day);
//        String answer = year + "-" + month + "-" + day;
//        return answer;
//    }

    /**
     * Inspired by http://nlp.stanford.edu/software/sutime.html
     *
     * @param text
     * @return
     */
    static Date extractTime(String text) {
//        Properties props = new Properties();
//        AnnotationPipeline pipeline = new AnnotationPipeline();
//        pipeline.addAnnotator(new TokenizerAnnotator(false));
//        pipeline.addAnnotator(new WordsToSentencesAnnotator(false));
//        pipeline.addAnnotator(new POSTaggerAnnotator(false));
//        pipeline.addAnnotator(new TimeAnnotator("sutime", props));
//
//        Annotation annotation = new Annotation(text);
//        annotation.set(CoreAnnotations.DocDateAnnotation.class, getTodaysDateString());
//        pipeline.annotate(annotation);
//        List<CoreMap> timexAnnsAll = annotation.get(TimeAnnotations.TimexAnnotations.class);
//
//        prettyPrint(annotation, timexAnnsAll);
//        return getDates(timexAnnsAll);
        String regex = "(((0?[1-9]|1[012])(:[0-5][0-9])?am)|((0?[0-9]|1[012])(:[0-5][0-9])?pm))";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        String answer = getFirstStringMatchingPattern(pattern, text);

        if (answer == null) {
            return null;
        }

        Date date = null;
        for (String format : TIME_FORMAT_LIST) {
            try {
                DateFormat sdf = new SimpleDateFormat(format);
                date = sdf.parse(answer);
                break;
            } catch (ParseException e) {
                //e.printStackTrace();
            }
        }
        if (date == null) {
            System.out.println("WARNING: detected date was not matched by any simple parsers.");
        }

        return date;
    }
}
