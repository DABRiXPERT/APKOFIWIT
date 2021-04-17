package com.buriku.nayoni.apkofiwit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

public class PROGRESSBAR_CTRL extends AppCompatActivity {

    TextView textView;
    ProgressBar progressBar;
    Context context;
    Intent intent;
    Bundle bundle;
    Bundle bundle_send;
    int mode;//0 = TIME, 1 = TILES
    int time;//FOR TIME(SEC)
    int note;//FOR TILES
    int mode_yellow;//1 = OFF, 0 = NORMAL, 2 = ON
    int mode_blue;//0 = OFF, 1 = ON
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertdialog_progress);

        intent = this.getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            mode = bundle.getInt("MODE");
            time = bundle.getInt("TIME");
            note = bundle.getInt("TILES");
            mode_yellow = bundle.getInt("YELLOW_ACTIVE");
            mode_blue = bundle.getInt("BLUE_ACTIVE");
        }
        context = getApplicationContext();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.ingame_gray)));
        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.ingame_status_gray));
        setTitle("GENERATING CHART...");

        textView = findViewById(R.id.text_progress);
        progressBar = findViewById(R.id.bar_progress);
        progressBar.setMax(24);
        progressBar.setProgress(0);
/*
        new AlertDialog.Builder(this)
                .setTitle("RESULT")
                .setMessage("MODE = "+mode+"\nTIME = "+time+"\nTILES = "+note+"\nYELLOW_ACTIVE = "+mode_yellow+"\nBLUE_ACTIVE = "+mode_blue)
                .setPositiveButton("GOODS", null)
                .show();
*/
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GENERATE_CHART();
            }
        });
        thread.start();
    }
    // HEAD OF: GENERATE_CHART
    public void GENERATE_CHART()
    {
        Random random = new Random();
        DecimalFormat tiles_reduce = new DecimalFormat("0.#####");//from 0.00000 to 1.00000
        DecimalFormat pct_reduce = new DecimalFormat("0.####");//only need XX.XX%
        double yellow_tiles = Double.parseDouble(tiles_reduce.format(random.nextDouble()));
        double yellow_pct = -1;
        double blue_tiles = Double.parseDouble(tiles_reduce.format(random.nextDouble()));
        double blue_pct = -1;
//----------------------------------------------------------------------------------------------------------------------MODE & TILE SELECT
        int mode = this.mode;//0 = TIME, 1 = TILES
        int time;//FOR TIME(SEC)
        int note;//FOR TILES
        int mode_yellow = this.mode_yellow;//1 = OFF, 0 = NORMAL, 2 = ON
        int mode_blue = this.mode_blue;//0 = OFF, 1 = ON
        if (mode == 0)
        {
            time = this.time;
            if (mode_yellow == 2)
                note = (random.nextInt(8) + 24) * time;//TIME = (24~32) * SECS
            else
                note = (random.nextInt(41) + 87) * time;//TIME = (87~128) * SECS
        }
        else
            note = this.note;//TOTAL NOTE
//----------------------------------------------------------------------------------------------------------------------MODE SET
        STATUS(1);
        if (mode_yellow == 1)
        {
            yellow_pct = 0;
        }
        if (mode_yellow == 2)
        {
            yellow_pct = 1;
        }
        if (mode_blue == 0)
        {
            blue_pct = 0;
        }
//----------------------------------------------------------------------------------------------------------------------AMOUNT SET
        STATUS(2);
        //PERCENTAGE: 1 - YELLOW = NORMAL
        //YELLOW: INDEPENDENT, PERCENTAGE: 13%(0~0.64999) / 10~20%(0.65~1) / 87%(0.755)
        //BLUE: APPEARS IN NORMAL, PERCENTAGE: 0~13%(default) / 13%~31%(0.87001~0.87999)  / 87%(0.87) / 100%(0.12345)
        //YELLOW PERCENTAGE
        if (mode_yellow == 0)
        {
            if (0 <= yellow_tiles && yellow_tiles < 0.65)
            {
                yellow_pct = 0.13;
            }
            else if (yellow_tiles == 0.755)
            {
                yellow_pct = 0.87;
            }
            else
            {
                yellow_pct = 0.1 + (0.1 * ((yellow_tiles - 0.65) / 0.35));
            }
        }
        //BLUE PERCENTAGE
        if (mode_blue == 1)
        {
            if (blue_tiles == 0.12345)
            {
                blue_pct = 1;
            }
            else if (blue_tiles == 0.87)
            {
                blue_pct = 0.87;
            }
            else if (0.87 < blue_tiles && blue_tiles < 0.88)
            {
                blue_pct = 0.13 + (0.18 * ((blue_tiles - 0.87) / 0.01));
            }
            else
            {
                blue_pct = 0.13 * blue_tiles;
            }
        }
        yellow_pct = Double.parseDouble(pct_reduce.format(yellow_pct));
        blue_pct = Double.parseDouble(pct_reduce.format(blue_pct));
        int yellow_amount = (int) (note * yellow_pct);
        int blue_amount = (int) (note * blue_pct);
        double[] blue_type = new double[blue_amount];
        double blue_amp_type;
        //BLUE AMOUNT
        for (int i = 0; i < blue_amount; i++)
        {
            blue_amp_type = Double.parseDouble(tiles_reduce.format(random.nextDouble()));
            if(0 <= blue_amp_type && blue_amp_type < 0.9)
            {
                blue_type[i] = 1.25;
            }
            else if (0.9 <= blue_amp_type && blue_amp_type < 0.95)
            {
                blue_type[i] = 1.5;
            }
            else if (0.95 <= blue_amp_type && blue_amp_type < 0.975)
            {
                blue_type[i] = 1.75;
            }
            else if (0.975 <= blue_amp_type && blue_amp_type < 0.99)
            {
                blue_type[i] = 2;
            }
            else if (0.99 <= blue_amp_type && blue_amp_type < 0.999)
            {
                blue_type[i] = 5;
            }
            else if (0.999 <= blue_amp_type && blue_amp_type < 1)
            {
                blue_type[i] = 10;
            }
            else if (blue_amp_type == 1)
            {
                blue_type[i] = 87;
            }
            else
            {
                blue_type[i] = -1;
            }
        }
        //PRINT MONI
        /*
        System.out.println("yellow_tiles = " + yellow_tiles);
        System.out.println("blue_tiles = " + blue_tiles);
        System.out.println("yellow_pct = " + yellow_pct * 100);
        System.out.println("blue_pct = " + blue_pct * 100);
        System.out.println();
        System.out.printf("%d / %d / %d\n", note, yellow_amount, blue_amount);
        for (int i = 0; i < blue_type.length; i++)
            System.out.println(i + " " + blue_type[i]);
        */
        STATUS(3);
        //GENERATE
        //System.out.print("\n");
        int[] amount_per_tile = new int[note];
        //ONLY USED IN THIS LOOP:
        int note_counter = note;
        int amt_length = 0;
        while (note_counter > 0)
        {
            amount_per_tile[amt_length] = random.nextInt(1000);
            if(0 <= amount_per_tile[amt_length] && amount_per_tile[amt_length] < 555)
                amount_per_tile[amt_length] = 1;
            else if(555 <= amount_per_tile[amt_length] && amount_per_tile[amt_length] < 876)
                amount_per_tile[amt_length] = 2;
            else if(876 <= amount_per_tile[amt_length] && amount_per_tile[amt_length] < 956)
                amount_per_tile[amt_length] = 3;
            else if(956 <= amount_per_tile[amt_length] && amount_per_tile[amt_length] < 1000)
                amount_per_tile[amt_length] = 4;
            else
                amount_per_tile[amt_length] = 0;

            //System.out.print(amount_per_tile[amt_length] + " ");
            note_counter -= amount_per_tile[amt_length];
            //System.out.println("note_counter = " + note_counter);
            amt_length++;
        }
        while (note_counter < 0)
        {
            int over_tile = random.nextInt(amt_length);
            if (amount_per_tile[over_tile] != 1)
            {
                amount_per_tile[over_tile]--;
                note_counter++;
            }
        }
        //System.out.print("\n");
        /*
        for (int i = 0; i < amount_per_tile.length; i++)
            System.out.print(amount_per_tile[i] + " ");
        */
        //System.out.println("\namt_length = " + amt_length);
        //DEPLOY
        int[][] chart = new int[amt_length][4];
        int[] deployed = new int[4];//AVOIDING SAME TILE SET IN LAST CHART#
        STATUS(4);
//----------------------------------------------------------------------------------------------------------------------INIT(0)
        for (int i = 0; i < amount_per_tile[0]; i++)//GENERATE
        {
            deployed[i] = random.nextInt(16) + 1;
            for (int j = 0; j < i; j++)//CHECK IF REPEATED
            {
                if (deployed[j] == deployed[i])
                {
                    deployed[i] = random.nextInt(16) + 1;
                    i--;
                }
            }
        }
        Arrays.sort(deployed, 0, amount_per_tile[0]);
        for (int i = 0; i < amount_per_tile[0]; i++)//LOAD IN
        {
            chart[0][i] = deployed[i];
        }
        STATUS(5);
//----------------------------------------------------------------------------------------------------------------------GENERATE(1~amt_length)
        for (int a = 1; a < amt_length; a++)
        {
            int[] same = new int[4];//AVOIDING SAME CHART
            for (int i = 0; i < amount_per_tile[a]; i++)//GENERATE
            {
                int flag = 0;
                same[i] = random.nextInt(16) + 1;
                for (int j = 0; j < i; j++)//CHECK IF REPEATED
                {
                    if (same[j] == same[i])
                    {
                        same[i] = random.nextInt(16) + 1;
                        flag++;
                    }
                }
                for (int k = 0; k < amount_per_tile[a]; k++)//COMPARE WITH LAST CHART
                {
                    if (deployed[k] == same[i])
                    {
                        same[i] = random.nextInt(16) + 1;
                        flag++;
                    }
                }
                if (flag > 0)//RE-GENERATE
                    i--;
            }
            Arrays.sort(same, 0, amount_per_tile[a]);
            for (int b = 0; b < 4; b++)
            {
                deployed[b] = same[b];
                chart[a][b] = same[b];
            }
        }
        STATUS(6);
//----------------------------------------------------------------------------------------------------------------------DEPLOY ORDER#
        int[][] order = new int[amt_length][4];
        int num = 1;
        for (int i = 0; i < amt_length; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if(chart[i][j] != 0)
                {
                    order[i][j] = num;
                    num++;
                }
            }
        }
        STATUS(7);
//----------------------------------------------------------------------------------------------------------------------DEPLOY YELLOW
        int[] yellows = new int[yellow_amount];
        boolean[][] yellow_coordinate = new boolean[amt_length][4];
        STATUS(4);
        for (int i = 0; i < yellow_amount; i++)//SET YELLOW ORDER
        {
            yellows[i] = random.nextInt(note) + 1;
            for (int j = 0; j < i; j++)
            {
                if(yellows[j] == yellows[i])
                {
                    yellows[i] = random.nextInt(note) + 1;
                    i--;
                }
            }
            if (i >= (yellow_amount - 1) * 0.25 && i < (yellow_amount - 1) * 0.5)
                STATUS(8);
            if (i >= (yellow_amount - 1) * 0.5 && i < (yellow_amount - 1) * 0.75)
                STATUS(9);
            if (i >= (yellow_amount - 1) * 0.75)
                STATUS(10);
        }
        Arrays.sort(yellows);
        STATUS(11);
        int current = 0;
        for (int i = 0; i < amt_length; i++)//COORDINATE-ize
        {
            for (int j = 0; j < 4; j++)
            {
                if (current >= yellows.length)
                    break;
                if(order[i][j] == yellows[current])
                {
                    yellow_coordinate[i][j] = true;
                    current++;
                }
                else
                    yellow_coordinate[i][j] = false;
            }
            if (i >= (amt_length - 1) * 0.25 && i < (amt_length - 1) * 0.5)
                STATUS(12);
            if (i >= (amt_length - 1) * 0.5 && i < (amt_length - 1) * 0.75)
                STATUS(13);
            if (i >= (amt_length - 1) * 0.75)
                STATUS(14);
        }
        STATUS(15);
//----------------------------------------------------------------------------------------------------------------------DEPLOY BLUE
        int[] blues = new int[blue_amount];
        double[][] blue_coordinate = new double[amt_length][4];
        for (int i = 0; i < blue_amount; i++)//SET BLUE ORDER
        {
            blues[i] = random.nextInt(note) + 1;
            for (int j = 0; j < i; j++)
            {
                if(blues[j] == blues[i])
                {
                    blues[i] = random.nextInt(note) + 1;
                    i--;
                }
            }
            if (i >= (amt_length - 1) * 0.25 && i < (amt_length - 1) * 0.5)
                STATUS(16);
            if (i >= (amt_length - 1) * 0.5 && i < (amt_length - 1) * 0.75)
                STATUS(17);
            if (i >= (amt_length - 1) * 0.75)
                STATUS(18);
        }
        Arrays.sort(blues);
        STATUS(19);
        current = 0;
        for (int i = 0; i < amt_length; i++)//COORDINATE-ize
        {
            for (int j = 0; j < 4; j++)
            {
                if (current >= blues.length)
                    break;
                if(order[i][j] == blues[current])
                {
                    blue_coordinate[i][j] = blue_type[current];
                    current++;
                }
                else
                    blue_coordinate[i][j] = 0;
            }
            if (i >= (amt_length - 1) * 0.25 && i < (amt_length - 1) * 0.5)
                STATUS(20);
            if (i >= (amt_length - 1) * 0.5 && i < (amt_length - 1) * 0.75)
                STATUS(21);
            if (i >= (amt_length - 1) * 0.75)
                STATUS(22);
        }
        STATUS(23);
        int[] chart_location = new int[note];
        int[] yellow_location = new int[yellow_amount];
        int[] blue_location = new int[blue_amount];
        double[] blue_multiplier = new double[blue_amount];
        int chart_count = 0;
        int yellow_count = 0;
        int blue_count = 0;
        //FORMAT: int => [frame no.]0[note] EXAMPLE: 1604 = 16-4
        for (int i = 0; i < amt_length; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if(chart[i][j] != 0)
                {
                    chart_location[chart_count] = i*100+chart[i][j];
                    chart_count++;
                }
                if (yellow_coordinate[i][j])
                {
                    yellow_location[yellow_count] = i*100+j;
                    yellow_count++;
                }
                if (blue_coordinate[i][j] != 0)
                {
                    blue_location[blue_count] = i*100+j;
                    blue_multiplier[blue_count] = blue_coordinate[i][j];
                    blue_count++;
                }
            }
        }
        /*
        for (int i = 0; i < yellow_location.length; i++)
            System.out.println("DAB_Y: "+yellow_location[i]);
        for (int i = 0; i < blue_location.length; i++)
        {
            System.out.println("DAB_BM: "+blue_location[i]);
            System.out.println("DAB_BL: "+blue_multiplier[i]);
        }
        System.out.println("========================================================================");
        for (int i = 0; i < amt_length; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                System.out.print(i + "-"+ j + "[" + order[i][j] + "]" + " | " + chart[i][j]);
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
        if (yellow_location == null)
            yellow_location[0] = -1;  //NO VALUE
        if (blue_location == null)
            blue_location[0] = -1;    //NO VALUE
        bundle_send = new Bundle();
        bundle_send.putInt("MODE", this.mode);
        bundle_send.putInt("TIME", this.time);
        bundle_send.putInt("TILES", this.note);
        bundle_send.putInt("YELLOW_ACTIVE", this.mode_yellow);
        bundle_send.putInt("BLUE_ACTIVE", this.mode_blue);
        bundle_send.putIntArray("CHART", chart_location);
        bundle_send.putIntArray("YELLOW", yellow_location);
        bundle_send.putIntArray("BLUE_L", blue_location);
        bundle_send.putDoubleArray("BLUE_M", blue_multiplier);
        bundle_send.putInt("AMT_LENGTH", amt_length);
        bundle_send.putInt("YELLOW_AMT", yellow_amount);
        bundle_send.putInt("BLUE_AMT", blue_amount);
        STATUS(24);
        Intent intent = new Intent(PROGRESSBAR_CTRL.this, GAMEPLAY.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtras(bundle_send);
        startActivity(intent);
        PROGRESSBAR_CTRL.this.finish();
    }

    // HEAD OF: STATUS
    public void STATUS(int stage)
    {
        String text_showing;
        switch (stage)
        {
            case 1:
                text_showing = "MODE SET";
                break;
            case 2:
                text_showing = "AMOUNT SET";
                break;
            case 3:
                text_showing = "GENERATE MAP 1/3";
                break;
            case 4:
                text_showing = "GENERATE MAP 2/3";
                break;
            case 5:
                text_showing = "GENERATE MAP 3/3";
                break;
            case 6:
                text_showing = "DEPLOY TILE 1/4";
                break;
            case 7:
                text_showing = "DEPLOY TILE 2/4 -  0%";
                break;
            case 8:
                text_showing = "DEPLOY TILE 2/4 - 12%";
                break;
            case 9:
                text_showing = "DEPLOY TILE 2/4 - 25%";
                break;
            case 10:
                text_showing = "DEPLOY TILE 2/4 - 37%";
                break;
            case 11:
                text_showing = "DEPLOY TILE 2/4 - 50%";
                break;
            case 12:
                text_showing = "DEPLOY TILE 2/4 - 62%";
                break;
            case 13:
                text_showing = "DEPLOY TILE 2/4 - 75%";
                break;
            case 14:
                text_showing = "DEPLOY TILE 2/4 - 87%";
                break;
            case 15:
                text_showing = "DEPLOY TILE 3/4 -  0%";
                break;
            case 16:
                text_showing = "DEPLOY TILE 3/4 - 12%";
                break;
            case 17:
                text_showing = "DEPLOY TILE 3/4 - 25%";
                break;
            case 18:
                text_showing = "DEPLOY TILE 3/4 - 37%";
                break;
            case 19:
                text_showing = "DEPLOY TILE 3/4 - 50%";
                break;
            case 20:
                text_showing = "DEPLOY TILE 3/4 - 62%";
                break;
            case 21:
                text_showing = "DEPLOY TILE 3/4 - 75%";
                break;
            case 22:
                text_showing = "DEPLOY TILE 3/4 - 87%";
                break;
            case 23:
                text_showing = "DEPLOY TILE 4/4";
                break;
            case 24:
                text_showing = "DONE";
                break;
            default:
                text_showing = "DEFAULT ERROR";
        }
        setTextView(textView, text_showing);
        progressBar.setProgress(stage);
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