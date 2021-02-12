package com.buriku.nayoni.apkofiwit;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MAIN_MENU extends Fragment {

    TextView version;
    Button start;
    MainActivity activity;
    String current_ver = "";
    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getFragmentManager().popBackStack();
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        start = view.findViewById(R.id.start_button);
        version = view.findViewById(R.id.version);
        if (getString(R.string.indev) == "true")
        {
            current_ver += "[CONSTRUCTING]\n";
        }
        current_ver += BuildConfig.VERSION_NAME;
        Log.w("DAB", "current = " + current_ver);
        version.setText(current_ver);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "PRESSED!", Snackbar.LENGTH_LONG).show();
            }
        });

        return view;
    }
}