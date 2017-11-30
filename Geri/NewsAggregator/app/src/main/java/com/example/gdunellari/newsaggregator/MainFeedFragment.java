package com.example.gdunellari.newsaggregator;

import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by davidschlegel on 11/30/17.
 */

public class MainFeedFragment extends ListFragment {
    String[] articleSamples = new String[] {
            new MainActivity.ArticleSample("1").toString(), new MainActivity.ArticleSample("2").toString()};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                    inflater.getContext(), android.R.layout.simple_list_item_1,
//                    articleSamples);
        final NewsViewAdapter la = new NewsViewAdapter(getContext());

        setListAdapter(la);

        final Bitmap imageBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.shumer_trump);
        la.add( new StoryLi(imageBitmap1, "Trump talks 'China GDP'", "President Trump said differences over policy..."));

        final Bitmap imageBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.us_stocks);
        la.add( new StoryLi(imageBitmap2, "U.S Stocks do well", "U.S Stocks rally to record levels never seen before..."));

        final Bitmap imageBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.gop_tax_plan);
        la.add( new StoryLi(imageBitmap3, "Eight senators work on tax plan", "Senate Republican leaders meet to speak over the latest tax plan..."));

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
