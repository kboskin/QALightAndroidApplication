package com.example.hp.qalightandroidapp;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by hp on 007 07.09.2017.
 */

public class LocalizationHelper {
    private Context context;

    public LocalizationHelper(Context context)
    {
        this.context = context;
    }

    public String getLanguage()
    {
        return Locale.getDefault().getDisplayLanguage();
    }
    public void setLanguage(String languageToLoad)
    {
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
}
