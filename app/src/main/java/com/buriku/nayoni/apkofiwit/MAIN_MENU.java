package com.buriku.nayoni.apkofiwit;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MAIN_MENU extends Fragment {

    TextView version;
    Button start;
    Button ranking;
    Button credits;
    MainActivity activity;
    String current_ver = "";
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getFragmentManager().popBackStack();
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        context = activity.getBaseContext();
        getActivity().setTitle("MAIN MENU");
        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.ingame_gray)));
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.ingame_status_gray));

        start = view.findViewById(R.id.start_button);
        ranking = view.findViewById(R.id.ranking);
        credits = view.findViewById(R.id.credits);
        version = view.findViewById(R.id.version);
        if (getString(R.string.indev) != "true")
        {
            current_ver += "[CONSTRUCTING]\n";
            Log.w("DAB", "indev true.");
        }
        current_ver += "ver: ";
        current_ver += BuildConfig.VERSION_NAME;
        Log.w("DAB", "current = " + current_ver);
        version.setText(current_ver);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                SETTING setting = new SETTING();
                fragmentTransaction.replace(R.id.place_view_in_here, setting);
                fragmentTransaction.commit();
            }
        });
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                CREDITS credits = new CREDITS();
                fragmentTransaction.replace(R.id.place_view_in_here, credits);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

}
/*
FragmentManager fragmentManager = getSupportFragmentManager();
FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
MAIN_MENU main_menu = new MAIN_MENU();
fragmentTransaction.add(R.id.place_view_in_here, main_menu);
fragmentTransaction.commit();
*/
/*
Context context = activity.getBaseContext();
activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.ingame_red)));
activity.getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.ingame_status_red));
*/