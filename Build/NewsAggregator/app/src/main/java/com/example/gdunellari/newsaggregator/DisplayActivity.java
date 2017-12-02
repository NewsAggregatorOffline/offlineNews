package com.example.gdunellari.newsaggregator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class DisplayActivity extends AppCompatActivity {

    private static WebView mWebView;
    private static StoryLi article;
    private static boolean readFromFile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mWebView = (WebView) findViewById(R.id.article_web_view);
        Bundle data = getIntent().getExtras();
        if(data != null) {
            article = (StoryLi) data.get("article");
            readFromFile = data.getBoolean("file");
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
