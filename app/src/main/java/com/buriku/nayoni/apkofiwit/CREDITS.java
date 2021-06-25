package com.buriku.nayoni.apkofiwit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CREDITS extends Fragment {

    TextView credits;
    Button exit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credits, container, false);
        getActivity().setTitle("ABOUT THIS GAME");
        credits = view.findViewById(R.id.text_credit_content);
        credits.setText("\n idkwtfisthis.apk\n\n\n CREATOR/PROGRAMMER: DABRiXPERT\n\n I/F DESIGN: DABRiXPERT\n\n GAME IDEA: DABRiXPERT\n\n PUBLISHER: RED COMPUTER CORP.\n \n TECNICAL SUPPORT: TEAM BRiXPERT\n\n\n\n R. C. C.  x  TEAM BRiXPERT\n\n FROM mid-2017 to mid-2021\n\n CC-AT/NC\n\n Social Network:\n\n Twitter/Instagram:\n @buriku_nayoni\n\n Facebook:\n fb.com/zxcvburikunayoniop\n\n GITHUB.io:\n dabrixpert.github.io\n\n\n\n Source Code:\n https://github.com/DABRiXPERT\n /APKOFIWIT\n");
        exit = view.findViewById(R.id.exit_from_credit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                MAIN_MENU main_menu = new MAIN_MENU();
                fragmentTransaction.replace(R.id.place_view_in_here, main_menu);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}