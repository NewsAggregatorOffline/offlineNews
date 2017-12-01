package com.example.gdunellari.newsaggregator;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by juneh on 11/30/2017.
 */

public class LoadArchiveTask extends AsyncTask<String, Float, StringBuilder> {

    private static final String TAG = "LOAD_ARCHIVE";
    private static String FILEPATH;
    private static Context context;

    public LoadArchiveTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        FILEPATH =  context.getFilesDir().getPath() + "/archived/";
    }

    @Override
    protected StringBuilder doInBackground(String... args) {
        Log.d(TAG, "reading from archive");

        String filename = args[0];
        StringBuilder page = new StringBuilder();
        try {
            page = (StringBuilder) deSerialization(FILEPATH + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return page;
    }


//--------------------------------------------------------------------------------------------------


    public static Object deSerialization(String file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        return object;
    }

//--------------------------------------------------------------------------------------------------

}
