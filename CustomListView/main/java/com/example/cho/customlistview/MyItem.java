package com.example.cho.customlistview;

import android.graphics.drawable.Drawable;

/**
 * Created by Cho on 2017-06-29.
 */

public class MyItem {

    private Drawable icon;
    private String name;
    private String contents;

    public Drawable getIcon(){ return icon; }

    public void setIcon(Drawable icon){this.icon = icon;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public String getContents(){return contents;}

    public void setContents(String contents){this.contents =contents;}
}
