package com.example.gdunellari.newsaggregator;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by juneh on 11/30/2017.
 */

public class ArchiveTask extends AsyncTask<String, Long, StringBuilder> {
    private static final String TAG = "ARCHIVE";
    private static String FILEPATH;

    private static Context context;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        FILEPATH =  context.getFilesDir().getPath() + "/archived/";
    }

    public ArchiveTask(Context context) {
        this.context = context;
    }

    @Override
    protected StringBuilder doInBackground(String... args) {
        Log.d(TAG, "reading from jsoup response");

        String url = args[0];
        String filename = args[1];
        StringBuilder page = new StringBuilder();

        try {
            Connection.Response response = Jsoup.connect(url)
                    .timeout(7000)
                    .execute();
            Document doc = response.parse();

            /* TODO catch NPE from null response */

            for (Element e : doc.getAllElements()) {
                page.append(e);
            }

            File filePath = new File(FILEPATH);
            if(!filePath.exists()){
                filePath.mkdirs();
            }
            serialization(new File(FILEPATH+filename), page);

            /* Usage on WebView Component */
            //            mWebView.post(new Runnable() {
            //                @Override
            //                public void run() {
            //                    mWebView.loadDataWithBaseURL(null, page.toString(), "text/html", "UTF-8", null);
            //                }
            //            });


        } catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return page;
    }

//--------------------------------------------------------------------------------------------------

    public static void serialization(File file, Object object) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

//--------------------------------------------------------------------------------------------------


}
