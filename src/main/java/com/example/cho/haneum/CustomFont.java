package com.example.cho.haneum;

import android.app.Application;
import android.graphics.Typeface;

import com.tsengvn.typekit.Typekit;

/**
 * Created by Cho on 2017-06-18.
 */

public class CustomFont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/bmjua.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/bmjua.ttf"));
    }
}
