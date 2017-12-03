package com.example.gdunellari.newsaggregator;

//import android.app.Fragment;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.app.Activity;
////import android.app.FragmentManager;
//import android.support.v4.app.FragmentManager;
//
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.widget.DrawerLayout;
//
//
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ImageButton;
//import android.widget.ListView;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {

    private boolean incognito;


    private final String[] fragmentNames = {
            "Home", "Archived",
            "Settings"};

    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        incognito = false;

        // Create the adapter for the FeedFragments
//        FeedFragmentAdapter feedFragmentAdapter = new FeedFragmentAdapter(getFragmentManager());
        FeedFragmentAdapter feedFragmentAdapter = new FeedFragmentAdapter(getFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(feedFragmentAdapter);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.drawer_list_item,
                fragmentNames));

        //Set last date Updated
        final TextView textView = (TextView) findViewById(R.id.textView);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:ms z");
        textView.setText("Last Update: " + sdf.format(new Date()));

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        final ImageButton incogBtn = (ImageButton) findViewById(R.id.imageButton2);
        incogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incognito == false) {
                    incognito = true;
                    Toast.makeText(getApplicationContext(),"Incognito Mode is On",Toast.LENGTH_LONG).show();
                    incogBtn.setBackgroundColor(Color.CYAN);
                    getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
                } else{
                    incognito = false;
                    Toast.makeText(getApplicationContext(),"Incognito Mode is Off",Toast.LENGTH_LONG).show();
                    incogBtn.setBackgroundColor(Color.TRANSPARENT);
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                }
            }
        });

        final ImageButton refreshBtn = (ImageButton) findViewById(R.id.imageButton3);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });


    }



    /**
     * Handle click on the NavigationDrawer
     */
    private void selectItem(int position) {

        // Highlight the selected item
        mDrawerList.setItemChecked(position, true);

        // Set the ViewPager's item to the currently selected position
        mViewPager.setCurrentItem(position);

        // Close the NavigationDrawer
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    /**
     * Listener that responds to clicks on the NavigationDrawer
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Adapter for FeedFragment view
     */
    public class FeedFragmentAdapter extends FragmentPagerAdapter {
        public FeedFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * getItem() is called to instantiate the fragment for the given FeedFragmentAdapter.
         *
         */

        @Override
        public Fragment getItem(int position) {
//            Log.i("fragment position: ", ""+position);

            //TODO: add the other fragments besides the main feed

//            if(position==1) {
//                return ArchiveFeedFragment.instantiate(getApplicationContext(), "archiveNews");
//            }else{
//                return MainFeedFragment.instantiate(getApplicationContext(), "news");
//
//            }

            return MainFeedFragment.instantiate(getApplicationContext(), "news");


        }

        @Override
        public int getCount() {
            return fragmentNames.length;
        }

        /*
         * Returns the title (name) for the current FeedFragment.
         */
        @Override
        public CharSequence getPageTitle(int position) {
//            Log.d("position: ", ""+position);

            return fragmentNames[position];
        }
    }


}
