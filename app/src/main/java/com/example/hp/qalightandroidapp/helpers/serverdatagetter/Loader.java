package com.example.hp.qalightandroidapp.helpers.serverdatagetter;

import android.app.Activity;
import android.content.Context;
import android.widget.Spinner;

import dmax.dialog.SpotsDialog;

/**
 * Created by hp on 024 24.11.2017.
 */

public class Loader {
    private SpotsDialog spotsDialog;
    Context context;
    private Activity referenceActivity;

    public Loader(Context context) {
        this.context = context;
        spotsDialog = new SpotsDialog(context, Spinner.MODE_DIALOG);
    }

    public void turnOnLoader()
    {
        referenceActivity = (Activity) context;
        referenceActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spotsDialog.show();
            }
        });

    }
    public void turnOffLoader()
    {
        referenceActivity = (Activity) context;
        referenceActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spotsDialog.dismiss();
            }
        });

    }
}
