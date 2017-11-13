package com.example.hp.qalightandroidapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.hp.qalightandroidapp.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by hp on 003 03.11.2017.
 */

public class DataGetterFromServer extends Thread {
    private String param;
    private String url;
    private Context context;
    private String responseData = "";
    private DataParser dataParser;

    public String getResponseData() {
        return responseData;
    }

    public DataGetterFromServer(String url, String param, Context context, DataParser dataParser) {
        this.param = param;
        this.url = url;
        this.context = context;
        this.dataParser = dataParser;
    }

    @Override
    public void run() {
        Request request = new Request.Builder()
                .url(url + param)
                .get()
                .build();
        OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (checkInternet(context)) {

            if (response != null) {
                if (response.code() == 200) {
                    try {
                        responseData = response.body().string();
                        dataParser.parseResponse(responseData);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Process the response Data
                    Log.d("ResponseData", responseData);
                } else {
                    //Server problem
                    Toast.makeText(context, context.getResources().getString(R.string.connection_problems) + " " + response.code(), Toast.LENGTH_SHORT).show();

                }


            } else {
                Activity getActivity = (Activity) context;
                getActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, context.getResources().getString(R.string.connection_problems), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
    }

    private boolean checkInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Log.d(TAG, "checkInternet: " + "Connected to WIFI");
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Log.d(TAG, "checkInternet: " + "Connected to Mobile data");
            }
            return true;
        } else {
            Log.d(TAG, "checkInternet: " + "Not connected");
            Toast.makeText(context, context.getString(R.string.internet_connection_failed), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
