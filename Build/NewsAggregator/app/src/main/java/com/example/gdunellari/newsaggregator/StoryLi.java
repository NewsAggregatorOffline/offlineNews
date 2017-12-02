package com.example.gdunellari.newsaggregator;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by davidschlegel on 11/28/17.
 */

public class StoryLi  implements Serializable{

    private Bitmap urlImage;
    private String urlArticle;
    private String title;
    private String description;
    private String archiveFilename;
    private boolean saved = false;

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

    public String getUrlArticle() {
        return urlArticle;
    }

    public String getArchiveFilename() {
        return archiveFilename;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
