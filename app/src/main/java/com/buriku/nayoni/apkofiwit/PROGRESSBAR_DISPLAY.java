package com.buriku.nayoni.apkofiwit;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PROGRESSBAR_DISPLAY {
    private static AlertDialog alertDialog;
    private static ProgressBar progressBar;

    public static void displayProgressStage(Context context, int stage)
    {
        if (alertDialog == null)
            alertDialog = new AlertDialog.Builder(context, R.style.ProgressBarInAlertDialog).create();

        View progressbar_view = LayoutInflater.from(context).inflate(R.layout.alertdialog_progress, null);
        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();

        String text_showing;
        progressBar = progressbar_view.findViewById(R.id.bar_progress);
        progressBar.setMax(24);
        progressBar.setProgress(0);
        switch (stage)
        {
            case 1:
                text_showing = "MODE SET";
                progressBar.setProgress(stage);
                break;
            case 2:
                text_showing = "AMOUNT SET";
                progressBar.setProgress(stage);
                break;
            case 3:
                text_showing = "GENERATE MAP 1/3";
                progressBar.setProgress(stage);
                break;
            case 4:
                text_showing = "GENERATE MAP 2/3";
                progressBar.setProgress(stage);
                break;
            case 5:
                text_showing = "GENERATE MAP 3/3";
                progressBar.setProgress(stage);
                break;
            case 6:
                text_showing = "DEPLOY TILE 1/4";
                progressBar.setProgress(stage);
                break;
            case 7:
                text_showing = "DEPLOY TILE 2/4 -  0%";
                progressBar.setProgress(stage);
                break;
            case 8:
                text_showing = "DEPLOY TILE 2/4 - 12%";
                progressBar.setProgress(stage);
                break;
            case 9:
                text_showing = "DEPLOY TILE 2/4 - 25%";
                progressBar.setProgress(stage);
                break;
            case 10:
                text_showing = "DEPLOY TILE 2/4 - 37%";
                progressBar.setProgress(stage);
                break;
            case 11:
                text_showing = "DEPLOY TILE 2/4 - 50%";
                progressBar.setProgress(stage);
                break;
            case 12:
                text_showing = "DEPLOY TILE 2/4 - 62%";
                progressBar.setProgress(stage);
                break;
            case 13:
                text_showing = "DEPLOY TILE 2/4 - 75%";
                progressBar.setProgress(stage);
                break;
            case 14:
                text_showing = "DEPLOY TILE 2/4 - 87%";
                progressBar.setProgress(stage);
                break;
            case 15:
                text_showing = "DEPLOY TILE 3/4 -  0%";
                progressBar.setProgress(stage);
                break;
            case 16:
                text_showing = "DEPLOY TILE 3/4 - 12%";
                progressBar.setProgress(stage);
                break;
            case 17:
                text_showing = "DEPLOY TILE 3/4 - 25%";
                progressBar.setProgress(stage);
                break;
            case 18:
                text_showing = "DEPLOY TILE 3/4 - 37%";
                progressBar.setProgress(stage);
                break;
            case 19:
                text_showing = "DEPLOY TILE 3/4 - 50%";
                progressBar.setProgress(stage);
                break;
            case 20:
                text_showing = "DEPLOY TILE 3/4 - 62%";
                progressBar.setProgress(stage);
                break;
            case 21:
                text_showing = "DEPLOY TILE 3/4 - 75%";
                progressBar.setProgress(stage);
                break;
            case 22:
                text_showing = "DEPLOY TILE 3/4 - 87%";
                progressBar.setProgress(stage);
                break;
            case 23:
                text_showing = "DEPLOY TILE 4/4";
                progressBar.setProgress(stage);
                break;
            case 24:
                text_showing = "DONE";
                progressBar.setProgress(stage);
                break;
            default:
                text_showing = "DEFAULT ERROR";
        }

        if (alertDialog == null)
            alertDialog = new AlertDialog.Builder(context, R.style.ProgressBarInAlertDialog).create();

        alertDialog.setView(progressbar_view, 0, 0, 0, 0);
        alertDialog.setContentView(progressbar_view, layoutParams);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        TextView progressbar_stattext = progressbar_view.findViewById(R.id.text_progress);
        progressbar_stattext.setText(text_showing);

        alertDialog.show();
        layoutParams.width = 667;
        layoutParams.height = 144;
        alertDialog.getWindow().setAttributes(layoutParams);
    }
    // AFTER ADDING THIS BUNCH OF CODE(FROM LINE 12 TO ?), I AM WONDERING WHY I DON'T USE SWITCH METHOD AT THE VERY BEGINNING...?
    // WTF AM I THINKING?
    // 2021.02.26 11:33 UTC+8

    public static void dismiss()
    {
        if (alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();
    }
}
/*
    STAGE
    0 = WTF IS THIS APP DOING RIGHT NOW\nSERIOUSLY
    1 = MODE SET
    2 = AMOUNT SET
    3 = GENERATE MAP 1/3
    4 = GENERATE MAP 2/3
    5 = GENERATE MAP 3/3
    6 = DEPLOY TILE 1/4
    7 = DEPLOY TILE 2/4 -  0%
    8 = DEPLOY TILE 2/4 - 12%
    9 = DEPLOY TILE 2/4 - 25%
    10 = DEPLOY TILE 2/4 - 37%
    11 = DEPLOY TILE 2/4 - 50%
    12 = DEPLOY TILE 2/4 - 62%
    13 = DEPLOY TILE 2/4 - 75%
    14 = DEPLOY TILE 2/4 - 87%
    15 = DEPLOY TILE 3/4 -  0%
    16 = DEPLOY TILE 3/4 - 12%
    17 = DEPLOY TILE 3/4 - 25%
    18 = DEPLOY TILE 3/4 - 37%
    19 = DEPLOY TILE 3/4 - 50%
    20 = DEPLOY TILE 3/4 - 62%
    21 = DEPLOY TILE 3/4 - 75%
    22 = DEPLOY TILE 3/4 - 87%
    23 = DEPLOY TILE 4/4
    24 = DONE
*/