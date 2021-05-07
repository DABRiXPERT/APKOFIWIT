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
    int mode_yellow;//1 = OFF, 0 = NORMAL, 2 = ON
    int mode_blue;//0 = OFF, 1 = ON
    int[] tmp_chart;
    int[] tmp_yellow;
    int[] tmp_blue_l;
    double[] tmp_blue_m;
    int amt_length;
    int yellow_amt;
    int blue_amt;
    //GAME VARIABLES
    int score = 0;
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

        // HEAD OF: GAME CONTROL
        Handler handler = new Handler();
        int[] note_remain = {note};
        for (int i = 0; i <= amt_length; i++) {
            int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setTitle("NOTE REMAINING: "+ note_remain[0] +" / "+note);
                    textView.setText("SCORE: "+score);
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
                    for (int i = 0; i < checkStatus.length; i++)
                        checkStatus[i] = 0;
                    if (finalI == amt_length)
                    {
                        // HEAD OF: RESULT(TMP)
                        Log.w("SOCRE/TOTAL", score+" "+(note*4));
                        double acc;
                        acc = (double)score / (double)(note*4);
                        Log.w("ACC: ", String.valueOf(acc));
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
                                .show();
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
                    }
                }
            }, 1000 * (i + 1));
        }
        // END OF: GAME CONTROL
    }

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
            score += 4;
        else
            score -= 3;

        textView.setText("SCORE: "+score);
        pressedButton.setBackgroundColor(getColor(R.color.ingame_gray));
    }
}