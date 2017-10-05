package com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask;

import android.text.Spanned;

import java.sql.Date;

/**
 * Created by hp on 014 14.09.2017.
 */

public class ModelHomeTask {
    private Spanned title;
    private String description;
    private String link;
    private int year;
    private int day;
    private int month;
    private Date date = new Date(getYear(), getMonth(), getDay());

    public ModelHomeTask(Spanned title, String description, int year, int month, int day) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.day = day;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Spanned getTitle() {
        return title;
    }

    public void setTitle(Spanned title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
