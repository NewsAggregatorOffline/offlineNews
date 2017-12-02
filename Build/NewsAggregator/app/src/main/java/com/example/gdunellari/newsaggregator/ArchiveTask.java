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

public class ArchiveTask extends AsyncTask<StoryLi, Long, Void> {
    private static final String TAG = "ARCHIVE";
    private static String filePath;

    private static Context context;

    public ArchiveTask(Context context, String filePath) {
        this.context = context;
        this.filePath =  context.getFilesDir().getPath() + filePath;
    }

    @Override
    protected Void doInBackground(StoryLi... args) {
        Log.d(TAG, "reading from jsoup response");

        StoryLi article = args[0];
        String filename = article.getArchiveFilename();
        String url = article.getUrlArticle();
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
            article.setData(page.toString());

            File dir = new File(filePath);
            if(!dir.exists()){
                dir.mkdirs();
            }

            serialization(new File(filePath+filename), article);


        } catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
