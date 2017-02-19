package com.example.kenedi.feedyoself;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FeedYoSelf";

    String[] infoArray = {"One", "Two", "Three", "Four"};
    int[] dateArray = {1, 2, 2, 3};
    String[] timeArray = {"1:00 - 2:00", "11:00 - 2:00", "4:00 - 5:00", "8:00 - 9:00"};
    int day = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ScrollView mainView = (ScrollView) findViewById(R.id.scroll);
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.ll);

        for(int i = 0; i < infoArray.length; i++){
            if(dateArray[i] != day){
                day = dateArray[i];
                LinearLayout a = new LinearLayout(this);
                a.setOrientation(LinearLayout.HORIZONTAL);
                TextView textView = new TextView(this);

                textView.setText(dateArray[i] + "Weekday");
                textView.setTextSize(30);
                a.addView(textView);
                mainLayout.addView(a);
            }

            LinearLayout b = new LinearLayout(this);
            b.setOrientation(LinearLayout.HORIZONTAL);
            TextView eventInfo = new TextView(this);
            TextView eventTime = new TextView(this);

            eventTime.setText(timeArray[i]);
            eventTime.setTextSize(15);
            eventTime.setPadding(20,30,30,30);

            eventInfo.setText(infoArray[i]);
            eventInfo.setTextSize(20);
            eventInfo.setPadding(10,10,10,10);
            eventInfo.setBackgroundColor(Color.parseColor("#95AB63"));

            b.addView(eventTime);
            b.addView(eventInfo);
            mainLayout.addView(b);
        }
    }

    public void sendMessage(View view){
        Intent intent = new Intent(MainActivity.this, FeedYoSelfMap.class);
        startActivity(intent);
    }
}
