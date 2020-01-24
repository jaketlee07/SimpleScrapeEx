package com.example.simplescrapeex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MoreNewsTopics extends AppCompatActivity
{
    static Button buttonPolitics;
    static int choice = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_news_topics);

        buttonPolitics = (Button)findViewById(R.id.politics);
    }
    public void scrapeFoxPolitics(View v) {
        choice = 3;
        final FoxNewsScrapeTask myScrape = new FoxNewsScrapeTask(this);
        myScrape.execute();
    }
}
