package com.example.gdunellari.newsaggregator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

class NewsViewAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private static final String TAG = "issues";
    private final List<StoryLi> mItems = new ArrayList<StoryLi>();
    private static HashMap<String, String> archiveMap;
    private final Context mContext;
    private static File archiveFile;


    public NewsViewAdapter(Context context, HashMap archiveMap) {
        mContext = context;

        mLayoutInflater = LayoutInflater.from(context);

        this.archiveMap = archiveMap;

        archiveFile = new File(context.getFilesDir().getPath() + "/archived/Archive.dat");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View dataView = convertView;

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
            viewHolder.saved = viewHolder.switchView.getShowText();

            dataView.setTag(viewHolder);

        }

        // Set the View's data

        // Retrieve the viewHolder Object
        ViewHolder storedViewHolder = (ViewHolder) dataView.getTag();
        StoryLi storyLi = mItems.get(position);

        Bitmap bitmap = null;
        try {
            bitmap = new ReadImageByteArrayTask().execute(storyLi.getUrlImage()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(bitmap != null) {storedViewHolder.image.setImageBitmap(bitmap);}
        storedViewHolder.text1.setText(storyLi.getTitle());
        if(storyLi.getDescription() != null && !storyLi.getDescription().equals("null")) {
            storedViewHolder.text2.setText(storyLi.getDescription());
        }else{
            storedViewHolder.text2.setText("");

        }

        storedViewHolder.switchView.setTag(position);
        storedViewHolder.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean on) {
                StoryLi storyLi = mItems.get((Integer) cb.getTag());

                if(on){
                    Log.i(TAG, "Switch is on!!" );
                    try {
                        if(!archiveMap.containsKey(storyLi.getTitle())) {
                            storyLi.setSaved(true);
                            Boolean success = new ArchiveTask(mContext).execute(storyLi).get();
                            if(success) {
                                archiveMap.put(storyLi.getTitle(), storyLi.getArchiveFilename());
                                ArchiveHelper.serialization(archiveMap, archiveFile);
                                Toast.makeText(mContext,"Story Saved",Toast.LENGTH_SHORT).show();

                            } else {
                                cb.setChecked(false);
                                Toast.makeText(mContext,"Failed to save story. \nCheck internet connection",Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "failed to save article" );
                        e.printStackTrace();
                    }

                }else{
                    if(archiveMap.containsKey(storyLi.getTitle())) {
                        Log.i(TAG, "Switch is off!!" );

                        try {
                            if(archiveMap.containsKey(storyLi.getTitle())) {
                                storyLi.setSaved(false);
                                archiveMap.remove(storyLi.getTitle());
                                /* TODO delete archive file */
                                new File(mContext.getFilesDir().getPath() + "/archived/" + storyLi.getArchiveFilename()).delete();
                                ArchiveHelper.serialization(archiveMap, archiveFile);
                                Toast.makeText(mContext,"Story Unsaved",Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });

        storedViewHolder.switchView.setChecked(storyLi.isSaved());

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
