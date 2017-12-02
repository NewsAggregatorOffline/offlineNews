package com.example.gdunellari.newsaggregator;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;

/**
 * Created by juneh on 12/2/2017.
 */

public class ReadImageByteArrayTask extends AsyncTask<byte[], Integer, Bitmap> {


    @Override
    protected Bitmap doInBackground(byte[]... args) {
        byte[] image = args[0];
        if(image != null) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return null;
    }

}
