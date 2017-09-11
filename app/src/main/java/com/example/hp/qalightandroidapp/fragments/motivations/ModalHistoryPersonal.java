package com.example.hp.qalightandroidapp.fragments.motivations;

import android.net.Uri;

/**
 * Created by root on 08.09.17.
 */

public class ModalHistoryPersonal {
    String foto;
    String name;
    String position;
    String history;

    public ModalHistoryPersonal(String foto, String name, String position, String history) {
        this.foto = foto;
        this.name=name;
        this.position=position;
        this.history = history;
    }

    public String getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
