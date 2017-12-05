package com.example.gdunellari.newsaggregator;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by davidschlegel on 11/30/17.
 */

public class ArchiveFeedFragment extends ListFragment {
    private static final String TAG = "ARCHIVE_FEED_FRAGMENT";
    private static final String API_ENDPOINT = "https://newsapi.org/v2/everything?q=bitcoin&sortBy=popularity&apiKey=62a0b24bfa1f4484bfa9043021f4e8c8";
    private static File archiveFile;
    private static JSONArray articles;
    private static Context mContext2;
    private  HashMap<String, String> archiveMap2;
    private  NewsViewAdapter newsViewAdapter2;



    public static ArchiveFeedFragment instantiate(Context context, String fname){
        //TODO: Do we need to do anything with contect and fname?
        Log.i("About to create", fname);
        ArchiveFeedFragment fragment = new ArchiveFeedFragment();
        mContext2 = context;
        archiveFile = new File(context.getFilesDir().getPath() + "/archived/Archive.dat");

        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(archiveFile.exists()) {
            try {
                archiveMap2 = (HashMap<String, String>) ArchiveHelper.deSerialization(archiveFile.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            archiveMap2 = new HashMap<>();
        }

        Log.i(TAG, "archive contains " + archiveMap2.size() + " items");

        newsViewAdapter2 = new NewsViewAdapter(mContext2, archiveMap2);
        setListAdapter(newsViewAdapter2);


        /* TODO Remove test data */
//        final Bitmap imageBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.shumer_trump);
//        final Bitmap imageBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.us_stocks);
//        final Bitmap imageBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.gop_tax_plan);
//        newsViewAdapter.add( new StoryLi(imageBitmap1, "Trump talks 'China GDP'", "President Trump said differences over policy..."));
//        newsViewAdapter2.add( new StoryLi(null,null, "U.S Stocks do well", "U.S Stocks rally to record levels never seen before..."));
//        newsViewAda pter.add( new StoryLi(imageBitmap3, "Eight senators work on tax plan", "Senate Republican leaders meet to speak over the latest tax plan..."));

        try {
//            JSONObject responseJson = (new ApiConnectionTask().execute(API_ENDPOINT)).get();
//            articles = responseJson.getJSONArray("articles");
//            if(articles != null) {
//                Log.i(TAG, "received response content of " + articles.length() + "articles");

                for(Map.Entry<String, String> entry : archiveMap2.entrySet()) {
                    Log.i(TAG, "title " + entry.getKey());
                    Log.i(TAG, "file: " + entry.getValue());
                    String filename = entry.getValue();
                    newsViewAdapter2.add(new LoadArchiveTask(mContext2).execute(filename).get());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }




    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

//        Log.i(TAG, "check class : " + this.getClass().getSimpleName());
        Log.i(TAG, "check var : " + MainActivity.mainMaxVal+"  "+ MainActivity.archiveMaxVal);


        StoryLi storyLi = (StoryLi) newsViewAdapter2.getItem(position);
        Intent intent = new Intent(ArchiveFeedFragment.this.getActivity(), DisplayActivity.class);
        intent.putExtra("fileName",storyLi.getArchiveFilename());
        intent.putExtra("url",storyLi.getUrlArticle());
        intent.putExtra("isSaved", storyLi.isSaved());
        startActivity(intent);
    }


//--------------------------------------------------------------------------------------------------
//
//    public static void serialization(Object object) {
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(archiveFile);
//            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
//            objectOutputStream.writeObject(object);
//            objectOutputStream.close();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static Object deSerialization(String file) throws IOException, ClassNotFoundException {
//        FileInputStream fileInputStream = new FileInputStream(file);
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
//        Object object = objectInputStream.readObject();
//        objectInputStream.close();
//        return object;
//    }
//
//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) mContext2.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

//--------------------------------------------------------------------------------------------------


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
