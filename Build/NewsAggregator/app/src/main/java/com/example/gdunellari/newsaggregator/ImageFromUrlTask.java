package com.example.gdunellari.newsaggregator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by juneh on 12/1/2017.
 */

public class ImageFromUrlTask extends AsyncTask<String, Float, byte[]> {
    @Override
    protected byte[] doInBackground(String... args) {
        try {
            URL url = new URL(args[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);

            return stream.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }
}
