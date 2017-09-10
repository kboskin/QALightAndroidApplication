package com.example.hp.qalightandroidapp.fragments.materialsandtests.materials.recyclerview;

import java.util.Date;

/**
 * Created by hp on 008 08.09.2017.
 */

public class ModelMaterials {
    private String title;
    private String description;
    private Date date;
    private String stringDate;

    public ModelMaterials(String title, String description, String stringDate) {
        this.title = title;
        this.description = description;
        this.stringDate = stringDate;
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
}
