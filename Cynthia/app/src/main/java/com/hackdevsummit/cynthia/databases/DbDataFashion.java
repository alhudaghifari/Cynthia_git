package com.hackdevsummit.cynthia.databases;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alhudaghifari on 11/22/2017.
 */

public class DbDataFashion extends RealmObject {

    @PrimaryKey
    private String id;
    private String linkGambar;
    private String judul;

    public DbDataFashion() {
    }

    public DbDataFashion(String id, String linkGambar, String judul) {
        this.id = id;
        this.linkGambar = linkGambar;
        this.judul = judul;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkGambar() {
        return linkGambar;
    }

    public void setLinkGambar(String linkGambar) {
        this.linkGambar = linkGambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
