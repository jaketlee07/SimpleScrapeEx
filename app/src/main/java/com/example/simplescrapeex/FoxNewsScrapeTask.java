package com.example.simplescrapeex;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;


public class FoxNewsScrapeTask extends AsyncTask<Void, Void, Void> {

    // Used this source to learn how to make an AsyncTask able to start an intent
    // https://stackoverflow.com/questions/25326617/start-another-activity-from-an-asynctask

    private final Activity mActivity;

    Document document;
    Elements tags, h2Tags;
    ArrayList<Story> myStories;

    String[] initialSelectString = {".collection.collection-article-list",
                                    ".collection.collection-must-read.js-must-read",
                                    ".collection.collection-opinion.has-load-more.js-opinion",
                                    ".collection.collection-section.politics",
                                    ".collection.collection-section.u-s",
                                    ".collection.collection-section.world",
                                    ".collection.collection-section.family",
                                    ".collection.collection-section.entertainment",
                                    ".collection.collection-section.sports",
                                    ".collection.collection-section.technology"};

    String[] categoryNames = {"Exclusive Clips", "Fox News Flash", "Opinions on Fox", "Politics", "United States", "World", "Family", "Entertainment", "Sports", "Technology"};

            /*
            *  index 0: Articles from the middle of the page
            *  index 1: Articles from Fox News Flash
            *  index 2: Articles from Opinion Section
            *  index 3: Articles from Politics
            *  index 4: Articles from United States
            *  index 5: Articles from World
            *  index 6: Articles from Family
            *  index 7: Articles from Entertainment
            *  index 8: Articles from Sports
            *  index 9: Articles from Technology
            */


    public FoxNewsScrapeTask(final Activity mActivity) {
        this.mActivity = mActivity;             // gets a reference to the Activity that this task was started from
        myStories = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String url = "https://www.foxnews.com/";

        try {
            document = Jsoup.connect(url).validateTLSCertificates(false).get();

            tags = document.select(initialSelectString[MainActivity.choice]);
            h2Tags = tags.select("h2");

            for (Element title : h2Tags) {
                String s = title.select(".title").text();
                // extract the href link that is inside the h2
                String link = title.select("a[href]").attr("href");
                Story story = new Story(s, link);
                myStories.add(story);
            }
        }
        catch (Exception e){
            Log.i("Denna", "Error: inside catch for doInBackground");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // Start Display intent to show Stories in a listview.  Waits to start intent until the data has finished scraping
        // https://stackoverflow.com/questions/25326617/start-another-activity-from-an-asynctask

        Intent intent = new Intent(this.mActivity.getBaseContext(), DisplayArticleActivity.class);
        intent.putExtra("stories", myStories);
        intent.putExtra("category", categoryNames[MainActivity.choice]);
        mActivity.startActivity(intent);
    }
}
