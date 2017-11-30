package com.example.gdunellari.newsaggregator;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(android.R.id.content) == null) {
            MainFeedFragment list = new MainFeedFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }



}
