package com.example.cho.bitcoin;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by Cho on 2017-09-06.
 */

public class FontCustom extends Application{

    @Override
    public void onCreate(){
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/bmjua.ttf"));
    }
}
