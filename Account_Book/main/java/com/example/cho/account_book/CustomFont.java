package com.example.cho.account_book;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by Cho on 2017-07-02.
 */

public class CustomFont extends Application{

    @Override
    public void onCreate(){
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/bmjua.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/bmjua.ttf"));

    }
}
