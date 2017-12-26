package com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerviewmaterials;

import java.util.Date;


/**
 * Created by hp on 008 08.09.2017.
 */

public class ModelMaterials {
    private String title;
    private String description;
    private Date date;
    private String stringDate;
    private String url;
    private int year;
    private int month;
    private int day;

    public ModelMaterials(String title, int year, int month, int day, String url) {
        this.title = title;
        this.url = url;
        this.year = year;
        this.month = month;
        this.day = day;
        this.stringDate = "" + getDay() + "/" + getMonth() + "/" + getYear();
    }

    public ModelMaterials(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public ModelMaterials(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    public ModelMaterials(String title, String stringDate) {
        this.title = title;
        this.stringDate = stringDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateInString(String stringDate) {
        this.stringDate = stringDate;
    }

    public String getDateInString() {
        return stringDate;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


    public String getUrl() {
        return url;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

}
