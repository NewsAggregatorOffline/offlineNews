package com.example.gdunellari.newsaggregator;

import android.graphics.Bitmap;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by davidschlegel on 11/28/17.
 */

public class StoryLi  implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public StoryLi createFromParcel(Parcel in) {
            return new StoryLi(in);
        }

        public StoryLi[] newArray(int size) {
            return new StoryLi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(urlArticle);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(archiveFilename);
//        parcel.writeB

    }

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

    public StoryLi(Parcel in){
        this.urlImage = in.readParcelable(Bitmap.class.getClassLoader());
        this.title = in.readString();
        this.description = in.readString();

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
