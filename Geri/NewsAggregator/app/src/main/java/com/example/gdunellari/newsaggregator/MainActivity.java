package com.example.gdunellari.newsaggregator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();

        if (fm.findFragmentById(android.R.id.content) == null) {
            SimpleListFragment list = new SimpleListFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    public static class ArticleSample {
        String text;

        public ArticleSample(String text) {
            this.text = text;
        }

        public String toString() {
            return text;
        }
    }

    public static class SimpleListFragment extends ListFragment {

        String[] articleSamples = new String[] {
                new ArticleSample("1").toString(), new ArticleSample("2").toString()};

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
}
