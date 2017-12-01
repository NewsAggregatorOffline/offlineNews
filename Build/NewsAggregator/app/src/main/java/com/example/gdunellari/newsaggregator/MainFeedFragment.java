package com.example.gdunellari.newsaggregator;

import android.app.ListFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by davidschlegel on 11/30/17.
 */

public class MainFeedFragment extends ListFragment {

     NewsViewAdapter newsViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         newsViewAdapter = new NewsViewAdapter(getContext());

        setListAdapter(newsViewAdapter);

        final Bitmap imageBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.shumer_trump);
        final Bitmap imageBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.us_stocks);
        final Bitmap imageBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.gop_tax_plan);

        newsViewAdapter.add( new StoryLi(imageBitmap1, "Trump talks 'China GDP'", "President Trump said differences over policy..."));
        newsViewAdapter.add( new StoryLi(imageBitmap2, "U.S Stocks do well", "U.S Stocks rally to record levels never seen before..."));
        newsViewAdapter.add( new StoryLi(imageBitmap3, "Eight senators work on tax plan", "Senate Republican leaders meet to speak over the latest tax plan..."));

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // position = individual list item
        // can also get individual list item by doing newsViewAdapter.get...()
        super.onListItemClick(l, v, position, id);


//        Log.d("CREATION", "HERE--------------------------------------------------");
        Toast.makeText(getContext(),"You just clicked the screen!",Toast.LENGTH_LONG).show();

//        View ib = l.findViewById(R.id.bodyText);
        //View ib = l.findViewById(R.id.urlImage);
//        if(ib==null) {
//            Toast.makeText(getContext(),"HERE2",Toast.LENGTH_LONG).show();
//        }


//        View ib = l.findViewById(R);
//        ib.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(),"HERE2",Toast.LENGTH_LONG).show();
//            }
//        });

//        final NewsViewAdapter.ViewHolder viewHolder = new NewsViewAdapter.ViewHolder();
//        viewHolder.switchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(),"HERE2",Toast.LENGTH_LONG).show();
//            }
//        });

    }

}
