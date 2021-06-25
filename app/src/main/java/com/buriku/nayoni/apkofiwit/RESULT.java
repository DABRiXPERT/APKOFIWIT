package com.buriku.nayoni.apkofiwit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.IntSummaryStatistics;

public class RESULT extends AppCompatActivity {

    TextView mode_display;
    TextView game_variables;
    TextView yellow_activation;
    TextView blue_activation;
    TextView score_integer;
    TextView score_decimal;
    TextView score_easy;
    TextView score_hard;
    TextView score_status;
    TextView perfect_plus_display;
    TextView perfect_display;
    TextView great_display;
    TextView good_display;
    TextView bad_display;
    TextView miss_display;
    TextView wrong_display;
    TextView combo_display;
    Button button;
    Intent intent;
    Bundle bundle;
    Context context;
    int mode;//0 = TIME, 1 = TILES
    int time;//FOR TIME(SEC)
    int note;//FOR TILES
    int mode_yellow;//0 = NORMAL, 1 = ON, 2 = OFF
    int mode_blue;//0 = OFF, 1 = ON
    int yellow_amt;
    //GAME VARIABLES
    int score;
    int p_perfect;//YELLOW ONLY
    int perfect;
    int great;
    int good;
    int bad;//YELLOW ONLY
    int miss;
    int wrong;
    int[] combo_streak;
    int combo_streak_length;
    int max_combo;
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null)
        {
            mode = bundle.getInt("MODE");
            mode_yellow = bundle.getInt("YELLOW_ACTIVE");
            mode_blue = bundle.getInt("BLUE_ACTIVE");
            time = bundle.getInt("TIME");
            note = bundle.getInt("TILES");
            yellow_amt = bundle.getInt("YELLOW_AMT");
            score = bundle.getInt("SCORE");
            p_perfect = bundle.getInt("P_PERFECT");
            perfect = bundle.getInt("PERFECT");
            great = bundle.getInt("GREAT");
            good = bundle.getInt("GOOD");
            bad = bundle.getInt("BAD");
            wrong = bundle.getInt("WRONG");
            miss = bundle.getInt("MISS");
            combo_streak = bundle.getIntArray("COMBO");
            combo_streak_length = bundle.getInt("COMBO_LEN");
        }
        context = getApplicationContext();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.ingame_gray)));
        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.ingame_status_gray));
        setTitle(R.string.result);

        mode_display = findViewById(R.id.textView);
        game_variables = findViewById(R.id.textView2);
        yellow_activation = findViewById(R.id.textView3);
        blue_activation = findViewById(R.id.textView4);
        score_integer = findViewById(R.id.textView5);
        score_decimal = findViewById(R.id.textView6);
        score_easy = findViewById(R.id.textView6E);
        score_hard = findViewById(R.id.textView6H);
        score_status = findViewById(R.id.textView6S);
        perfect_plus_display = findViewById(R.id.textView14);
        perfect_display = findViewById(R.id.textView15);
        great_display = findViewById(R.id.textView16);
        good_display = findViewById(R.id.textView17);
        bad_display = findViewById(R.id.textView18);
        miss_display = findViewById(R.id.textView19);
        wrong_display = findViewById(R.id.textView20);
        combo_display = findViewById(R.id.textView21);
        button = findViewById(R.id.button);
        // END OF: INITIALIZE

        // HEAD OF: MAX COMBO
        for (int i = 0; i < combo_streak_length; i++)
            Log.i("DAB_CS", String.valueOf(combo_streak[i]));
        IntSummaryStatistics statistics = Arrays.stream(combo_streak).summaryStatistics();
        max_combo = statistics.getMax();
        // HEAD OF: INFORMATION DISPLAY
        /* == MODE == */
        if (mode == 0)
        {
            mode_display.setText("MODE: TIME");
            /* == TIME == */
            game_variables.setText(String.format("%2d min %02d sec", time/60, time%60));
        }
        if (mode == 1)
        {
            mode_display.setText("MODE: TILES");
            /* == TILES == */
            game_variables.setText(String.format("%d tls.", note));
        }
        /* == YELLOW ACTIVE == */
        if (mode_yellow == 0)
            yellow_activation.setText(R.string.yl_nml);
        if (mode_yellow == 1)
            yellow_activation.setText(R.string.yl_on);
        if (mode_yellow == 2)
            yellow_activation.setVisibility(View.INVISIBLE);
        /* == BLUE ACTIVE == */
        if (mode_blue == 0)
            blue_activation.setVisibility(View.INVISIBLE);
        if (mode_blue == 1)
            blue_activation.setVisibility(View.VISIBLE);
        /* == SCORE == */
        float easy, hard, normal, yellow_total, tiles;
        if (mode_yellow == 2)
            yellow_total = 1;
        else
            yellow_total = yellow_amt;
        tiles = note + wrong;
        easy = (float)((((p_perfect+perfect)* 1 + great * 0.768 + good * 0.49725 + bad * 0.27075 + miss * 0 + wrong * 0)/tiles) + ((p_perfect * 0.28)/yellow_total));
        easy *= 100;
        score_easy.setText(String.format("EASY: %4.4f %3s", easy, RANK_RETURN(1, easy)));
        float combo_percentage = 0;
        for (int i = 0; i < combo_streak_length; i++) {
            combo_percentage += combo_streak[i];
            Log.w("DAB/FINAL_COMBO_STREAK", String.format("%d: %d", i, combo_streak[i]));
        }
        combo_percentage /= tiles;
        hard = (float)(p_perfect * 8 + perfect * 4 + great * 2 + good * 1 + bad * 0 + miss * (-3) + wrong * (-5)) * combo_percentage;
        hard = (hard / (yellow_amt * 8 + (tiles - yellow_amt) * 4)) * 100;
        score_hard.setText(String.format("HARD: %5.4f %3s", hard, RANK_RETURN(2, hard)));
        normal = (easy+hard) / 2;
        normal = Float.parseFloat(String.format("%03.4f", normal));
        int normal_int = (int)(normal*10000);
        String si_text = "";
        if (normal < 0 && -1 < normal)
            si_text += "-";
        si_text += String.format("%2d", normal_int/10000);
        score_integer.setText(si_text);
        score_decimal.setText(String.format(".%04d%%", Math.abs(normal_int%10000)));
        /* == STATUS == */
        float finalNormal = normal;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;)
                {
                    try {
                        setTextView(score_status, String.format("NORMAL RANK: %3s", RANK_RETURN(0, finalNormal)));
                        Thread.sleep(1000);
                        if (SP_RETURN() != "")
                            setTextView(score_status, SP_RETURN());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        /* == DISPLAY == */
        perfect_plus_display.setText(String.valueOf(p_perfect));
        perfect_display.setText(String.valueOf(perfect));
        great_display.setText(String.valueOf(great));
        good_display.setText(String.valueOf(good));
        bad_display.setText(String.valueOf(bad));
        miss_display.setText(String.valueOf(miss));
        wrong_display.setText(String.valueOf(wrong));
        /* == BACK TO MENU == */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RESULT.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                RESULT.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setTitle(R.string.exit)
            .setMessage(R.string.before_exiting_the_app)
            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .setNegativeButton(R.string.no, null)
            .show();
    }

    /* == RANK == */
    // DIFF: 0 = NORMAL; 1 = EASY; 2 = HARD
    public String RANK_RETURN(int diff, float score)
    {
        if (diff == 0)
        {
            if(107.75 <= score && score <= 114)
                return "A++";
            else if (95 <= score && score < 107.75)
                return "A+";
            else if (85.5 <= score && score < 95)
                return "A";
            else if (74 <= score && score < 85.55)
                return "B";
            else if (60 <= score && score < 74)
                return "C";
            else if (46.75 <= score && score < 60)
                return "D";
            else if (35 <= score && score < 46.75)
                return "E";
            else if (23.5 <= score && score < 35)
                return "E-";
            else
                return "F";
        }
        if (diff == 1)
        {
            if(117.25 <= score && score <= 128)
                return "A++";
            else if (104 <= score && score < 117.25)
                return "A+";
            else if (94.5 <= score && score < 104)
                return "A";
            else if (84 <= score && score < 94.5)
                return "B";
            else if (72 <= score && score < 84)
                return "C";
            else if (60 <= score && score < 72)
                return "D";
            else if (48 <= score && score < 60)
                return "E";
            else if (36 <= score && score < 48)
                return "E-";
            else
                return "F";
        }
        if(diff == 2)
        {
            if(92.5 <= score && score <= 100)
                return "A++";
            else if (85.5 <= score && score < 92.5)
                return "A+";
            else if (75 <= score && score < 85.5)
                return "A";
            else if (66.25 <= score && score < 75)
                return "B";
            else if (56.75 <= score && score < 66.25)
                return "C";
            else if (48 <= score && score < 56.75)
                return "D";
            else if (37.25 <= score && score < 48)
                return "E";
            else if (28.5 <= score && score < 37.25)
                return "E-";
            else
                return "F";
        }
        return "X";
    }

    public String SP_RETURN()
    {
        String status = "";
        if (p_perfect == note && perfect == 0 && great == 0 && good == 0 && bad == 0 && miss == 0 && max_combo == note && combo_streak[0] == note && wrong == 0)
            status += getString(R.string.all_perfect_plus);
        else if (p_perfect + perfect == note && great == 0 && good == 0 && bad == 0 && miss == 0 && max_combo == note && combo_streak[0] == note && wrong == 0)
            status += getString(R.string.all_perfect);
        else if (p_perfect + perfect + great + good == note && bad == 0 && miss == 0 && max_combo == note && combo_streak[0] == note && wrong == 0)
            status += getString(R.string.full_combo);
        else if (p_perfect + perfect + great + good + bad == note && miss == 0 && max_combo == note - bad && combo_streak[0] == note - bad && wrong == 0)
            status += getString(R.string.full_combo_minus);
        else if (wrong == 0)
            status += getString(R.string.no_wrong);
        else
            status += "";

        return status;
    }

    private void setTextView(final TextView textView, final String value)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(value);
            }
        });
    }
}