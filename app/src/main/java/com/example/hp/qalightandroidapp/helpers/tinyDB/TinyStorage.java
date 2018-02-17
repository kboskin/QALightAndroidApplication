package com.example.hp.qalightandroidapp.helpers.tinyDB;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hp on 014 14.02.2018.
 */

public class TinyStorage {
    public static void storeList(Context context, String key, ArrayList<?> arrayList)
    {
        TinyDB tb = new TinyDB(context);
        for (int i = 0; i < arrayList.size(); i++)
        {
            Log.d("List is", arrayList.get(i).toString());
        }
        Log.d("Saving list:" + key, arrayList.toString());
        tb.putListObject(key, arrayList);
    }

    public static ArrayList<?> retrieveList(Context context, String key, Class className)
    {
        TinyDB tb = new TinyDB(context);
        Log.d("Retrieving list:" + key, tb.getListObject(key, className).toString());
        return tb.getListObject(key, className);
    }
}
