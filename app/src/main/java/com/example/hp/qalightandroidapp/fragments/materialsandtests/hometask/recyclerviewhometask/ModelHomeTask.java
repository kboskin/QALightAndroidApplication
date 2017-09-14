package com.example.hp.qalightandroidapp.fragments.materialsandtests.hometask.recyclerviewhometask;

/**
 * Created by hp on 014 14.09.2017.
 */

public class ModelHomeTask {
    private String title;
    private String description;

    public ModelHomeTask(String title, String description)
    {
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
