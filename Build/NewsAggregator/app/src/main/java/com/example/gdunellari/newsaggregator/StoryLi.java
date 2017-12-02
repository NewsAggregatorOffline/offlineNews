package com.example.gdunellari.newsaggregator;

import android.graphics.Bitmap;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by davidschlegel on 11/28/17.
 */

public class StoryLi  implements Serializable{

//    TODO remove Parcelable overrides
//    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
//        public StoryLi createFromParcel(Parcel in) {
//            return new StoryLi(in);
//        }
//
//        public StoryLi[] newArray(int size) {
//            return new StoryLi[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//
//        parcel.writeString(urlArticle);
//        parcel.writeString(title);
//        parcel.writeString(description);
//        parcel.writeString(archiveFilename);
//        parcel.writeB
//
//    }

    private byte[] urlImage;
    private String urlArticle;
    private String title;
    private String description;
    private String archiveFilename;
    private String data;
    private boolean saved = false;

    public StoryLi(String urlArticle, byte[] urlImage, String title, String description) {
        this.urlArticle = urlArticle;
        this.urlImage = urlImage;
        this.title = title;
        this.description = description;
        archiveFilename = title.trim().replaceAll("[^A-Za-z]+","");
    }

    public byte[] getUrlImage() {
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

    public String getData() {
        return data;
    }

    public void setData(String data){
        this.data = data;
    }
}
