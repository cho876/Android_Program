package com.example.cho.between;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by Cho on 2017-08-14.
 */

public class Custom_Font extends Application{
    @Override
    public void onCreate(){
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/bmjua.ttf"));
    }
}
