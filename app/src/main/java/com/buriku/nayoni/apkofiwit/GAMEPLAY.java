package com.buriku.nayoni.apkofiwit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GAMEPLAY extends AppCompatActivity {

    TextView textView;
    TextView textView_combo;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button10;
    Button button11;
    Button button12;
    Button button13;
    Button button14;
    Button button15;
    Button button16;
    Intent intent;
    Bundle bundle;
    Context context;
    int mode;//0 = TIME, 1 = TILES
    int time;//FOR TIME(SEC)
    int note;//FOR TILES
    int mode_yellow;//0 = NORMAL, 1 = ON, 2 = OFF
    int mode_blue;//0 = OFF, 1 = ON
    int[] tmp_chart;
    int[] tmp_yellow;
    int[] tmp_blue_l;
    double[] tmp_blue_m;
    int amt_length;
    int yellow_amt;
    int blue_amt;
    //GAME VARIABLES
    int[] note_remain = {-1};
    int score = 0;
    int p_perfect = 0;// YELLOW ONLY
    int perfect = 0;
    int great = 0;
    int good = 0;
    int bad = 0;//YELLOW ONLY
    int miss = 0;
    int wrong = 0;
    int combo_current = 0;
    int[] combo_streak;
    int combo_streak_length = 1;
    int[] checkStatus = new int[16];//0 = NONE, 1 = NORMAL, 2 = YELLOW, 3 = BLUE
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null)
        {
            mode = bundle.getInt("MODE");
            time = bundle.getInt("TIME");
            note = bundle.getInt("TILES");
            mode_yellow = bundle.getInt("YELLOW_ACTIVE");
            mode_blue = bundle.getInt("BLUE_ACTIVE");
            tmp_chart = bundle.getIntArray("CHART");
            tmp_yellow = bundle.getIntArray("YELLOW");
            tmp_blue_l = bundle.getIntArray("BLUE_L");
            tmp_blue_m = bundle.getDoubleArray("BLUE_M");
            amt_length = bundle.getInt("AMT_LENGTH");
            yellow_amt = bundle.getInt("YELLOW_AMT");
            blue_amt = bundle.getInt("BLUE_AMT");
        }
        context = getApplicationContext();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.ingame_gray)));
        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.ingame_status_gray));
        textView = findViewById(R.id.stat_text);
        textView_combo = findViewById(R.id.text_combo);
        button1 = findViewById(R.id.ctrl1);
        button2 = findViewById(R.id.ctrl2);
        button3 = findViewById(R.id.ctrl3);
        button4 = findViewById(R.id.ctrl4);
        button5 = findViewById(R.id.ctrl5);
        button6 = findViewById(R.id.ctrl6);
        button7 = findViewById(R.id.ctrl7);
        button8 = findViewById(R.id.ctrl8);
        button9 = findViewById(R.id.ctrl9);
        button10 = findViewById(R.id.ctrl10);
        button11 = findViewById(R.id.ctrl11);
        button12 = findViewById(R.id.ctrl12);
        button13 = findViewById(R.id.ctrl13);
        button14 = findViewById(R.id.ctrl14);
        button15 = findViewById(R.id.ctrl15);
        button16 = findViewById(R.id.ctrl16);
        // END OF: INITIALIZATION
        //System.out.println("========================================================================");
        for (int i = 0; i < checkStatus.length; i++)
            checkStatus[i] = 0;
        Log.e("DAB_THREARD", Looper.myLooper() == Looper.getMainLooper()?"1":"0");
        // HEAD OF: TRUE ARRAY VARIABLES
        int[][] chart = new int[amt_length][4];
        boolean[][] yellow_coordinate = new boolean[amt_length][4];
        double[][] blue_coordinate = new double[amt_length][4];
        // HEAD OF: REASSEMBLE LOCATION TO COORDINATE
        int tmp_f = -1, tmp_i = 0;
        for (int i = 0; i < tmp_chart.length; i++)
        {
            int x, y;
            x = tmp_chart[i] / 100;
            if (tmp_f == -1)//INIT
            {
                tmp_f = x;
            }
            if (tmp_f == x)//SAME
            {
                y = tmp_chart[i] % 100;
                chart[x][tmp_i] = y;
                tmp_i++;
            }
            else
            {
                tmp_f = tmp_chart[i] / 100;
                tmp_i = 0;
                i--;
            }
        }
        for (int i = 0; i < amt_length; i++)//INIT
            for (int j = 0; j < 4; j++)
                yellow_coordinate[i][j] = false;
        for (int i = 0; i < tmp_yellow.length; i++)
        {
            int x = tmp_yellow[i] / 100;
            int y = tmp_yellow[i] % 100;
            yellow_coordinate[x][y] = true;
        }
        for (int i = 0; i < tmp_blue_l.length; i++)
        {
            int x = tmp_blue_l[i] / 100;
            int y = tmp_blue_l[i] % 100;
            blue_coordinate[x][y] = tmp_blue_m[i];
        }

        for (int i = 0; i < amt_length; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                System.out.print(i + "-"+ j + " | " + chart[i][j]);
                if (yellow_coordinate[i][j])
                {
                    System.out.print(" YL");
                }
                if (blue_coordinate[i][j] != 0)
                {
                    System.out.print(" x" + blue_coordinate[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }
        // END OF: REASSEMBLE
        note_remain[0] = note;
        combo_streak = new int[140574];
        // HEAD OF: GAME CONTROL
        Log.w("DAB_CSL", String.valueOf(combo_streak_length));
        Handler handler = new Handler();
        for (int i = 0; i <= amt_length; i++) {
            int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setTitle("NOTE REMAINING: "+ note_remain[0] +" / "+note);
                    textView.setText("SCORE: "+score);
                    textView_combo.setText("COMBO: "+combo_current);
                    // HEAD OF: DETCET UNCLEAR TILES
                    for (int k = 0; k < checkStatus.length; k++)
                    {
                        if (checkStatus[k] != 0)
                        {
                            miss++;
                            MISS_WRONG();
                            score -= 3;
                            Log.e("DAB_CSL", String.valueOf(combo_streak_length));
                            Log.w("DAB_TILE", "NOT PRESSED! -3");
                        }
                    }
                    textView.setText("SCORE: "+score);
                    textView_combo.setText("COMBO: "+combo_current);
                    // HEAD OF: INITIALIZE
                    button1.setBackgroundColor(getColor(R.color.ingame_gray));
                    button2.setBackgroundColor(getColor(R.color.ingame_gray));
                    button3.setBackgroundColor(getColor(R.color.ingame_gray));
                    button4.setBackgroundColor(getColor(R.color.ingame_gray));
                    button5.setBackgroundColor(getColor(R.color.ingame_gray));
                    button6.setBackgroundColor(getColor(R.color.ingame_gray));
                    button7.setBackgroundColor(getColor(R.color.ingame_gray));
                    button8.setBackgroundColor(getColor(R.color.ingame_gray));
                    button9.setBackgroundColor(getColor(R.color.ingame_gray));
                    button10.setBackgroundColor(getColor(R.color.ingame_gray));
                    button11.setBackgroundColor(getColor(R.color.ingame_gray));
                    button12.setBackgroundColor(getColor(R.color.ingame_gray));
                    button13.setBackgroundColor(getColor(R.color.ingame_gray));
                    button14.setBackgroundColor(getColor(R.color.ingame_gray));
                    button15.setBackgroundColor(getColor(R.color.ingame_gray));
                    button16.setBackgroundColor(getColor(R.color.ingame_gray));
                    for (int l = 0; l < checkStatus.length; l++)
                        checkStatus[l] = 0;
                    if (finalI == amt_length)
                    {
                        // HEAD OF: RESULT(TMP)
                        combo_streak[combo_streak_length-1] = combo_current;
                        Log.w("SOCRE/TOTAL", score+" "+(note*4));
                        double acc;
                        acc = (double)score / (double)(note*4);
                        Log.w("ACC: ", String.valueOf(acc));

                        // TODO: 2021-05-15 RESULT PAGE
                        // HEAD OF: RESULT PAGE, 2021-05-20
                        Intent intent = new Intent(GAMEPLAY.this, RESULT.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("MODE", mode);
                        bundle.putInt("YELLOW_ACTIVE", mode_yellow);
                        bundle.putInt("BLUE_ACTIVE", mode_blue);
                        bundle.putInt("TIME", time);
                        bundle.putInt("TILES", note);
                        bundle.putInt("SCORE", score);
                        bundle.putInt("YELLOW_AMT", yellow_amt);
                        bundle.putInt("P_PERFECT", p_perfect);
                        bundle.putInt("PERFECT", perfect);
                        bundle.putInt("GREAT", great);
                        bundle.putInt("GOOD", good);
                        bundle.putInt("BAD", bad);
                        bundle.putInt("MISS", miss);
                        bundle.putInt("WRONG", wrong);
                        for (int i = 0; i < combo_streak_length; i++) {
                            Log.w("DAB/COMBO_STREAK", String.format("%d: %d", i, combo_streak[i]));
                        }
                        bundle.putIntArray("COMBO", combo_streak);
                        bundle.putInt("COMBO_LEN", combo_streak_length);
                        new AlertDialog.Builder(GAMEPLAY.this)
                                .setTitle("GAME OVER!!!")
                                .setMessage("ACCURACY: "+String.format("%.4f", acc*100)+" %\n"+"SCORE: "+score)
                                .setPositiveButton("BRUH", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        GAMEPLAY.this.finish();
                                    }
                                })
                                .setCancelable(false)
                                .show();
                        /*
                        LEGACY - 2021.05.07
                        
                        new AlertDialog.Builder(GAMEPLAY.this)
                                .setTitle("RESULT(TMP)")
                                .setMessage("ACC: "+String.format("%.4f", acc*100)+" %")
                                .setPositiveButton("GOODZ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(GAMEPLAY.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        GAMEPLAY.this.finish();
                                    }
                                })
                                .setCancelable(false)
                                .show();
                        */
                    }
                    else
                    {
                        // HEAD OF: TAGGING
                        int chk_note_amt = 4;
                        for (int j = 0; j < chart[finalI].length; j++)
                        {
                            switch (chart[finalI][j])
                            {
                                case 1:
                                    button1.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 2:
                                    button2.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 3:
                                    button3.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 4:
                                    button4.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 5:
                                    button5.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 6:
                                    button6.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 7:
                                    button7.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 8:
                                    button8.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 9:
                                    button9.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 10:
                                    button10.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 11:
                                    button11.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 12:
                                    button12.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 13:
                                    button13.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 14:
                                    button14.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 15:
                                    button15.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                case 16:
                                    button16.setBackgroundColor(getColor(R.color.ingame_blue));
                                    break;
                                default:
                                    chk_note_amt--;
                            }
                            if (chart[finalI][j] != 0)
                                checkStatus[chart[finalI][j]-1] = 1;
                        }
                        Log.i("CHKNOTE", String.valueOf(chk_note_amt));
                        note_remain[0] -= chk_note_amt;
                        textView.setText("SCORE: "+score);
                        textView_combo.setText("COMBO: "+combo_current);
                    }
                }
            }, 878 * (i + 1));
        }
        // END OF: GAME CONTROL
    }

    // TODO: 2021-05-07 JUDGEMENT
    // REMARK: onclick BUTTON!
    public void DETECT(View view) {
        Button pressedButton = findViewById(view.getId());
        int buttonID = view.getId();
        int onPressed = -1;
        switch (buttonID)
        {
            case R.id.ctrl1:
                onPressed = 0;
                break;
            case R.id.ctrl2:
                onPressed = 1;
                break;
            case R.id.ctrl3:
                onPressed = 2;
                break;
            case R.id.ctrl4:
                onPressed = 3;
                break;
            case R.id.ctrl5:
                onPressed = 4;
                break;
            case R.id.ctrl6:
                onPressed = 5;
                break;
            case R.id.ctrl7:
                onPressed = 6;
                break;
            case R.id.ctrl8:
                onPressed = 7;
                break;
            case R.id.ctrl9:
                onPressed = 8;
                break;
            case R.id.ctrl10:
                onPressed = 9;
                break;
            case R.id.ctrl11:
                onPressed = 10;
                break;
            case R.id.ctrl12:
                onPressed = 11;
                break;
            case R.id.ctrl13:
                onPressed = 12;
                break;
            case R.id.ctrl14:
                onPressed = 13;
                break;
            case R.id.ctrl15:
                onPressed = 14;
                break;
            case R.id.ctrl16:
                onPressed = 15;
                break;
            default:
                //NONE
        }
        if (checkStatus[onPressed] == 1)
        {
            score += 4;
            perfect++;
            combo_current++;
            Log.w("DAB_TILE", "PRESSED BLUE! +4");
        }
        else
        {
            wrong++;
            MISS_WRONG();
            score -= 5;
            Log.e("DAB_CSL", String.valueOf(combo_streak_length));
            Log.w("DAB_TILE", "PRESSED GRAY! -5");
        }
        textView.setText("SCORE: "+score);
        textView_combo.setText("COMBO: "+combo_current);
        pressedButton.setBackgroundColor(getColor(R.color.ingame_gray));
        checkStatus[onPressed] = 0;
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

    // HEAD OF: MISS/WRONG TO CLEAR CURRENT COMBO
    public void MISS_WRONG()
    {
        combo_streak[combo_streak_length-1] = combo_current;
        combo_streak_length++;
        combo_current = 0;
    }


    // TODO: 2021-06-11 INSTANT END (TEMP/TEST RESULT PAGE) 
    public void INSTANT_END(View view) {
        Intent intent = new Intent(GAMEPLAY.this, RESULT.class);
        Bundle bundle = new Bundle();
        int[] combo_tmp = {2};
        bundle.putInt("MODE", 1);
        bundle.putInt("YELLOW_ACTIVE", 0);
        bundle.putInt("BLUE_ACTIVE", 0);
        bundle.putInt("TIME", 0);
        bundle.putInt("TILES", 2);
        bundle.putInt("SCORE", 4);
        bundle.putInt("YELLOW_AMT", 2);
        bundle.putInt("P_PERFECT", 0);
        bundle.putInt("PERFECT", 2);
        bundle.putInt("GREAT", 0);
        bundle.putInt("GOOD", 0);
        bundle.putInt("BAD", 0);
        bundle.putInt("MISS", 0);
        bundle.putInt("WRONG", 0);
        bundle.putIntArray("COMBO", combo_tmp);
        bundle.putInt("COMBO_LEN", combo_tmp.length);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtras(bundle);
        startActivity(intent);
        GAMEPLAY.this.finish();
    }

}