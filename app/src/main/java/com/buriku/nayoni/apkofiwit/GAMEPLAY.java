package com.buriku.nayoni.apkofiwit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class GAMEPLAY extends AppCompatActivity {

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

        //System.out.println("========================================================================");

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
        /*
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
        */
        // END OF: REASSEMBLE

        // HEAD OF: GAME CONTROL

    }
}