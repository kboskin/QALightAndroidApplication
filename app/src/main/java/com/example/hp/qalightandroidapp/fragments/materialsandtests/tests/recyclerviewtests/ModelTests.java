package com.example.hp.qalightandroidapp.fragments.materialsandtests.tests.recyclerviewtests;

/**
 * Created by hp on 012 12.09.2017.
 */


public class ModelTests {

    private String title;
    private String description;

    public ModelTests(String title, String description)
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
