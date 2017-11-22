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
}
