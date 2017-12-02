package com.example.gdunellari.newsaggregator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by juneh on 12/2/2017.
 */

public class ReadImageByteArrayTask extends AsyncTask<byte[], Float, Bitmap> {
    @Override
    protected Bitmap doInBackground(byte[]... args) {
        byte[] image = args[0];
        if(image != null) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return null;
    }
}
