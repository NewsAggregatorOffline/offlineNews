package com.example.gdunellari.newsaggregator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

class NewsViewAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private static final String TAG = "issues";
    final List<StoryLi> mItems = new ArrayList<StoryLi>();
    private final Context mContext;



    public NewsViewAdapter(Context context) {
        mContext = context;

        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View dataView = convertView;
        StoryLi storyLi = mItems.get(position);

        // Check for recycled View
        if (null == dataView) {

            // Not recycled. Create the View
            dataView = mLayoutInflater.inflate(R.layout.news_story_list_item, parent, false);


            // Cache View information in ViewHolder Object
            ViewHolder viewHolder = new ViewHolder();

            ImageView imageView = (ImageView) dataView.findViewById(R.id.urlImage);
            TextView textView1 = (TextView) dataView.findViewById(R.id.headlineText);
            TextView textView2 = (TextView) dataView.findViewById(R.id.bodyText);

            viewHolder.text1 = textView1;
            viewHolder.text2 = textView2;
            viewHolder.image = imageView;

            viewHolder.switchView = dataView.findViewById(R.id.saveSwitch);
            viewHolder.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton cb, boolean on) {

                    if(on){
                        Log.i(TAG, "Switch is on!!" );
                        Toast.makeText(mContext,"Story Saved",Toast.LENGTH_LONG).show();

                    }else{
                        Log.i(TAG, "Switch is off!!" );
                        Toast.makeText(mContext,"Story Unsaved",Toast.LENGTH_LONG).show();


                    }

                }
            });
            viewHolder.saved = viewHolder.switchView.getShowText();

            dataView.setTag(viewHolder);

        }

        // Set the View's data

        // Retrieve the viewHolder Object
        ViewHolder storedViewHolder = (ViewHolder) dataView.getTag();

        storedViewHolder.image.setImageBitmap(storyLi.getUrlImage());
        storedViewHolder.text1.setText(storyLi.getTitle());
        storedViewHolder.text2.setText(storyLi.getDescription());
        storedViewHolder.switchView.setChecked(storyLi.getIsSaved());

        //Set the data in the data View
        return dataView;
    }

    // The ViewHolder class. See:
    // http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
    static class ViewHolder {
        public TextView text1;
        public TextView text2;
        public ImageView image;
        Switch switchView;
        public boolean saved;

    }

    @Override
    public Object getItem(int pos) {

        return mItems.get(pos);

    }
    // Get the ID for the ToDoItem
    // In this case it's just the position

    @Override
    public long getItemId(int pos) {

        return pos;

    }

    @Override
    public int getCount() {

        return mItems.size();

    }

    // Clears the list adapter of all items.

    public void clear() {

        mItems.clear();
        notifyDataSetChanged();

    }

    // Notify observers that the data set has changed

    public void add(StoryLi item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

}
