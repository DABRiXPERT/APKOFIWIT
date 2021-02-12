package com.buriku.nayoni.apkofiwit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("DAB", "NAME: " + BuildConfig.VERSION_NAME);
        Log.w("DAB", "CODE: " + BuildConfig.VERSION_CODE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MAIN_MENU main_menu = new MAIN_MENU();
        fragmentTransaction.add(R.id.place_view_in_here, main_menu);
        fragmentTransaction.commit();
    }

}