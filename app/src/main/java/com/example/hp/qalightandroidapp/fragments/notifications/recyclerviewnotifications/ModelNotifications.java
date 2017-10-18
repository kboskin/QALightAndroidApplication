package com.example.hp.qalightandroidapp.fragments.notifications.recyclerviewnotifications;

/**
 * Created by hp on 017 17.10.2017.
 */

public class ModelNotifications {
    private String title;
    private String description;

    public ModelNotifications(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {

        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {

        this.title = title;
    }
}
