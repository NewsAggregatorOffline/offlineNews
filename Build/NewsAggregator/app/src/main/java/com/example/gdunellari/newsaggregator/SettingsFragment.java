package com.example.gdunellari.newsaggregator;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.io.File;

/**
 * Created by davidschlegel on 12/4/17.
 */

public class SettingsFragment extends Fragment {

    int mainMaxValSet;
    int archiveMaxValSet;

    public static SettingsFragment instantiate(Context context, String fname){
        //TODO: Do we need to do anything with contect and fname?
        Log.i("About to create", fname);
        SettingsFragment fragment = new SettingsFragment();

        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        NumberPicker numberPicker = view.findViewById(R.id.numberPicker);
        NumberPicker numberPicker2 = view.findViewById(R.id.numberPicker7);
        ImageButton imageButton = view.findViewById(R.id.imageButton);

//        ImageView imageView = view.findViewById(R.id.imageView5);

//        if(savedInstanceState != null) {
//            MainActivity.mainMaxVal = savedInstanceState.getInt("mainMaxVal");
//            archiveMaxVal = savedInstanceState.getInt("archiveMaxVal");
//        }else {
//            mainMaxVal = 0;
//            archiveMaxVal = 0;
//        }


        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(30);

        numberPicker.setEnabled(true);
        numberPicker.setWrapSelectorWheel(true);


        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
//                Toast.makeText(getContext(),"Selected: " + newVal,Toast.LENGTH_SHORT).show();

                mainMaxValSet = newVal;
            }
        });


        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(30);

        numberPicker2.setEnabled(true);
        numberPicker2.setWrapSelectorWheel(true);

        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                archiveMaxValSet = newVal;
//                Toast.makeText(getContext(),"Selected: " + newVal,Toast.LENGTH_SHORT).show();

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"Saved Settings: " ,Toast.LENGTH_SHORT).show();

//                Bundle bundle = new Bundle();
//                bundle.putInt("mainMaxVal",mainMaxVal);
//                bundle.putInt("archiveMaxVal",archiveMaxVal);


                MainActivity.mainMaxVal = mainMaxValSet;
                MainActivity.archiveMaxVal = archiveMaxValSet;

//                Toast.makeText(getContext(),"Saved Settings: main = "+mainMaxValSet+ " archive = "+ archiveMaxValSet ,Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(),"Saved Settings: main = "+ MainActivity.mainMaxVal+ " archive = "+ MainActivity.archiveMaxVal ,Toast.LENGTH_SHORT).show();



            }
        });

        numberPicker.setValue(MainActivity.mainMaxVal);
        numberPicker2.setValue(MainActivity.archiveMaxVal);
        Log.i("Sett", "check var : " + MainActivity.mainMaxVal+"  "+ MainActivity.archiveMaxVal);
        Log.i("Sett", "check var 2 : " + numberPicker.getValue()+"  "+ numberPicker2.getValue());




//        return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//
//        savedInstanceState.putInt("mainMaxVal",mainMaxVal);
//        savedInstanceState.putInt("archiveMaxVal",archiveMaxVal);
//
//        super.onSaveInstanceState(savedInstanceState);
//
//    }

}
