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

import java.util.ArrayList;
import java.util.List;

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

        // Check for recycled View
        if (null == dataView) {

            // Not recycled. Create the View
            dataView = mLayoutInflater.inflate(R.layout.news_story_list_item, parent, false);
            final StoryLi storyLi = mItems.get(position);


            // Cache View information in ViewHolder Object
            final ViewHolder viewHolder = new ViewHolder();

            final ImageView imageView = (ImageView) dataView.findViewById(R.id.urlImage);

            final TextView textView1 = (TextView) dataView.findViewById(R.id.headlineText);
            final TextView textView2 = (TextView) dataView.findViewById(R.id.bodyText);


            imageView.setImageBitmap(storyLi.getUrlImage());
            textView1.setText(storyLi.getTitle());
            textView2.setText(storyLi.getDescription());

            viewHolder.switchView = dataView.findViewById(R.id.saveSwitch);


            viewHolder.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton cb, boolean on) {
//                    Log.i(TAG, "inside click liskener" );

                    if(on){
                        Log.i(TAG, "Switch is on!!" );
                        viewHolder.saved = true;
                        storyLi.setSaved(true);
                        Toast.makeText(mContext,"Story Saved",Toast.LENGTH_LONG).show();

                    }else{
                        Log.i(TAG, "Switch is off!!" );
                        viewHolder.saved = false;
                        storyLi.setSaved(false);
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

        //Set the data in the data View


        return dataView;
    }

    // The ViewHolder class. See:
    // http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
    static class ViewHolder {
        public TextView text;
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
