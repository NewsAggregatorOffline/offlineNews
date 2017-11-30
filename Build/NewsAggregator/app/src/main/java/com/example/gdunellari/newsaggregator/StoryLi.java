package com.example.gdunellari.newsaggregator;

import android.graphics.Bitmap;

/**
 * Created by davidschlegel on 11/28/17.
 */

public class StoryLi {

    private Bitmap urlImage;
    private String title;
    private String description;


    public StoryLi(Bitmap urlImage, String title, String description) {
        this.urlImage = urlImage;
        this.title = title;
        this.description = description;
    }

    public Bitmap getUrlImage() {
        return urlImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
