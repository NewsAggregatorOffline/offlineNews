package com.example.gdunellari.newsaggregator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "DISPLAY_ACTIVITY";
    private static WebView mWebView;
    private static StoryLi article;
    private static boolean readFromFile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        mWebView = (WebView) findViewById(R.id.article_web_view);
        initWebView(mWebView, true);
        Bundle data = getIntent().getExtras();
        String filename =  data.getString("filename");
        String url = data.getString("url");
        File file = new File(getApplicationContext().getFilesDir().getPath() + "/archived/" + filename);
        if(file.exists()) {
            try {
                article = new LoadArchiveTask(this,filename).get();
                mWebView.loadDataWithBaseURL(null, article.getData(), "text/html", "UTF-8", null);

            } catch (Exception e) {
                Log.i(TAG,"failed to read from file");
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this,"Loading URL: " + url,Toast.LENGTH_LONG).show();
            Log.i(TAG,"Loading URL: " + url);

            if(url != null && URLUtil.isValidUrl(url)) {
                mWebView.loadUrl(data.getString("url"));
            } else {
                Log.i(TAG,"url is not valid");
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void initWebView(WebView mWebView, boolean enabled) {
        mWebView.setWebChromeClient(new WebChromeClient());
//
//        Force using cache resources, even if they have expired
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

//         More settings
        mWebView.getSettings().setDomStorageEnabled(enabled);
        mWebView.getSettings().setAllowFileAccess(enabled);
        mWebView.getSettings().setJavaScriptEnabled(enabled);
    }

}
