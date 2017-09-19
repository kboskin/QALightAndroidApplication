package com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask;

import android.text.Spanned;

/**
 * Created by hp on 014 14.09.2017.
 */

public class ModelHomeTask {
    private Spanned title;
    private String description;
    private String link;

    public ModelHomeTask(Spanned title, String description/*, String link*/)
    {
        this.title = title;
        this.description = description;
        this.link = link;
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
