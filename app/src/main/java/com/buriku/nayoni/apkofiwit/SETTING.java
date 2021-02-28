package com.buriku.nayoni.apkofiwit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

public class SETTING extends Fragment {

    RadioButton mode_time;
    RadioButton mode_tiles;
    ConstraintLayout layout_details;
    SeekBar diff_set;
    TextView diff_moni;
    TextView judge_text;
    Spinner set_judge;
    CheckBox blue_active;
    Button start_game;
    Button exit;
    int mode;
    int secs;
    int tiles;
    int set = 3;
    int yellow_active;
    int amp_active;
    String moni_display;
    String[] judge_type = {"NORMAL", "ON", "OFF"};
    MainActivity activity;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        context = activity.getBaseContext();
        getActivity().setTitle("GAME SETUP");

        mode_time = view.findViewById(R.id.mode_time);
        mode_tiles = view.findViewById(R.id.mode_tiles);
        layout_details = view.findViewById(R.id.mode_details);
        diff_set = view.findViewById(R.id.change_diff);
        diff_moni = view.findViewById(R.id.limit_text);
        judge_text = view.findViewById(R.id.judge_text);
        set_judge = view.findViewById(R.id.set_judgement);
        blue_active = view.findViewById(R.id.blue_active);
        start_game = view.findViewById(R.id.start_the_game);
        exit = view.findViewById(R.id.exit_from_setup);

        mode_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_details.setVisibility(View.VISIBLE);
                mode = 0;
                int secs;
                switch (set)
                {
                    case 0:
                        secs = 30;
                        break;
                    case 1:
                        secs = 40;
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        secs = 45 + ((set - 2) * 15);
                        break;
                    case 6:
                        secs = 100;
                        break;
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        secs = 105 + ((set - 7) * 15);
                        break;
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                        secs = 180 + ((set - 12) * 30);
                        break;
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                        secs = 450 + ((set - 17) * 150);
                        break;
                    case 21:
                        secs = 1000;
                        break;
                    default:
                        secs = 0;
                }
                moni_display = String.format(" TIME SET:%2d m %02d s. ", secs/60, secs%60);
                start_game.setEnabled(true);
                diff_moni.setText(moni_display);
            }
        });
        mode_tiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_details.setVisibility(View.VISIBLE);
                mode = 1;
                int tiles;
                switch (set)
                {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        tiles = 25 + (set * 25);
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        tiles = 200 + ((set - 6) * 50);
                        break;
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                        tiles = 600 + ((set - 13) * 100);
                        break;
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                        tiles = 1250 + ((set - 18) * 250);
                        break;
                    default:
                        tiles = 0;
                }
                moni_display = String.format(" TILES SET: %4d tls. ", tiles);
                start_game.setEnabled(true);
                diff_moni.setText(moni_display);
            }
        });

        diff_set.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                set = progress;
                if (mode == 0)
                {
                    switch (set)
                    {
                        case 0:
                            secs = 30;
                            break;
                        case 1:
                            secs = 40;
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            secs = 45 + ((set - 2) * 15);
                            break;
                        case 6:
                            secs = 100;
                            break;
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                            secs = 105 + ((set - 7) * 15);
                            break;
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                            secs = 180 + ((set - 12) * 30);
                            break;
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                            secs = 450 + ((set - 17) * 150);
                            break;
                        case 21:
                            secs = 1000;
                            break;
                        default:
                            secs = 0;
                    }
                    moni_display = String.format(" TIME SET:%2d m %02d s. ", secs/60, secs%60);
                    diff_moni.setText(moni_display);
                }
                if (mode == 1)
                {
                    switch (set)
                    {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            tiles = 25 + (set * 25);
                            break;
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                            tiles = 200 + ((set - 6) * 50);
                            break;
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                            tiles = 600 + ((set - 13) * 100);
                            break;
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                            tiles = 1250 + ((set - 18) * 250);
                            break;
                        default:
                            tiles = 0;
                    }
                    moni_display = String.format(" TILES SET: %4d tls. ", tiles);
                    diff_moni.setText(moni_display);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //DON'T NEED THIS
                //BUT MAY BE USEFUL IN FUTURE...?
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //DON'T NEED THIS
                //BUT MAY BE USEFUL IN FUTURE...?
            }
        });

        ArrayAdapter judgeAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, judge_type);
        set_judge.setAdapter(judgeAdapter);
        set_judge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    judge_text.setText("There are 10%-20% of chance that YELLOW TILES appears (But mostly 13%).");
                if (position == 1)
                    judge_text.setText("There are 100% of chance that YELLOW TILES appears.");
                if (position == 2)
                    judge_text.setText("There are 0% of chance that YELLOW TILES appears.");
                yellow_active = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        blue_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Snackbar.make(view, "ON!", Snackbar.LENGTH_SHORT).show();
                    amp_active = 1;
                }
                else
                {
                    Snackbar.make(view, "OFF!", Snackbar.LENGTH_SHORT).show();
                    amp_active = 0;
                }
            }
        });

        start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                IN_GAME in_game = new IN_GAME();

                Bundle bundle = new Bundle();
                bundle.putInt("MODE", mode);
                bundle.putInt("TIME", secs);
                bundle.putInt("TILES", tiles);
                bundle.putInt("YELLOW_ACTIVE", yellow_active);
                bundle.putInt("BLUE_ACTIVE",amp_active);

                in_game.setArguments(bundle);
                fragmentTransaction.replace(R.id.place_view_in_here, in_game);
                fragmentTransaction.commit();
            }
        });

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