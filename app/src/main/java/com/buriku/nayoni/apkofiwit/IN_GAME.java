package com.buriku.nayoni.apkofiwit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IN_GAME extends Fragment {

    MainActivity activity;
    Context context;
    int mode;
    int time;
    int tiles;
    int yellow_active;
    int blue_active;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            mode = bundle.getInt("MODE");
            time = bundle.getInt("TIME");
            tiles = bundle.getInt("TILES");
            yellow_active = bundle.getInt("YELLOW_ACTIVE");
            blue_active = bundle.getInt("BLUE_ACTIVE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getFragmentManager().popBackStack();
        View view =  inflater.inflate(R.layout.fragment_in_game, container, false);
        context = getActivity();
        /*
        for (int i = 0; i <= 24; i++)
        {
            int finalI = i;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PROGRESSBAR_DISPLAY.displayProgressStage(context, finalI);
                }
            }, 500 * (i + 1));
        }
        */
        PROGRESSBAR_DISPLAY.dismiss();

        new AlertDialog.Builder(context)
                .setTitle("RESULT")
                .setMessage("MODE = "+mode+"\nTIME = "+time+"\nTILES = "+tiles+"\nYELLOW_ACTIVE = "+yellow_active+"\nBLUE_ACTIVE = "+blue_active)
                .setPositiveButton("GOODS", null)
                .show();
        return view;
    }

}