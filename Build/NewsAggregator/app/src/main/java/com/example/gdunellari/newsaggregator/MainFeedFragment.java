package com.example.gdunellari.newsaggregator;

import android.app.LauncherActivity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by davidschlegel on 11/30/17.
 */

public class MainFeedFragment extends ListFragment {
    private static final String TAG = "FEED_FRAGMENT";
    private static final String API_ENDPOINT = "https://newsapi.org/v2/everything?q=bitcoin&sortBy=popularity&apiKey=62a0b24bfa1f4484bfa9043021f4e8c8";
    private static JSONArray articles;
    private static NewsViewAdapter newsViewAdapter;
    private static Context mContext;



    public static MainFeedFragment instantiate(Context context, String fname){
        //TODO: Do we need to do anything with contect and fname?
        MainFeedFragment fragment = new MainFeedFragment();
        mContext = context;
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         newsViewAdapter = new NewsViewAdapter(mContext);

        setListAdapter(newsViewAdapter);


        /* TODO Remove test data */
//        final Bitmap imageBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.shumer_trump);
//        final Bitmap imageBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.us_stocks);
//        final Bitmap imageBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.gop_tax_plan);
//        newsViewAdapter.add( new StoryLi(imageBitmap1, "Trump talks 'China GDP'", "President Trump said differences over policy..."));
//        newsViewAdapter.add( new StoryLi(imageBitmap2, "U.S Stocks do well", "U.S Stocks rally to record levels never seen before..."));
//        newsViewAdapter.add( new StoryLi(imageBitmap3, "Eight senators work on tax plan", "Senate Republican leaders meet to speak over the latest tax plan..."));

        try {
            JSONObject responseJson = (new ApiConnectionTask().execute(API_ENDPOINT)).get();
            articles = responseJson.getJSONArray("articles");
            if(articles != null) {
                Log.i(TAG, "received response content of " + articles.length() + "articles");
                for(int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.getJSONObject(i);
                    byte[] imageByteArray = new ImageFromUrlTask().execute(article.getString("urlToImage")).get();
                    newsViewAdapter.add( new StoryLi(article.getString("url"),imageByteArray, article.getString("title"), article.getString("description")));
                }
            } else {
                Log.i(TAG, "received null response");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }




    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        StoryLi storyLi = (StoryLi) newsViewAdapter.getItem(position);
        Intent intent = new Intent(MainFeedFragment.this.getActivity(), DisplayActivity.class);
        intent.putExtra("filePath",storyLi.getArchiveFilename());
        intent.putExtra("url",storyLi.getUrlArticle());
        startActivity(intent);
    }




}




/*
For onListItemClick...

Testing Code:
Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
Log.d("CREATION", "HERE--------------------------------------------------");
Log.d("CREATION", "This is wut it iz returnin: "+ newsViewAdapter.getItem(position).getClass().getSimpleName() );
Toast.makeText(getContext(), "This is wut it iz returnin: "+ newsViewAdapter.getItem(position).getClass().getSimpleName(), Toast.LENGTH_SHORT).show();


Parameters:
position = individual list item
can also get individual list item by doing newsViewAdapter.get...()
*/
