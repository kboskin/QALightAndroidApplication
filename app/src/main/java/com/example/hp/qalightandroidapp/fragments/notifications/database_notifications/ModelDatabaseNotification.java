package com.example.hp.qalightandroidapp.fragments.notifications.database_notifications;

/**
 * Created by hp on 018 18.10.2017.
 */

public class ModelDatabaseNotification {
    private int _id;
    private String _title;
    private String _description;

    public ModelDatabaseNotification()
    {

    }

    public ModelDatabaseNotification(int _id, String _title, String _description) {
        this._id = _id;
        this._title = _title;
        this._description = _description;
    }
    public ModelDatabaseNotification(String _title, String _description)
    {
        this._title = _title;
        this._description = _description;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }
}
