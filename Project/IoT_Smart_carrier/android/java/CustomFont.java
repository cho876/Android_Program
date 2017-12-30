package com.example.skcho.smartcarrier;

/**
 * Created by skCho on 2017-12-28.
 * <p>
 * Set Custom Font Class
 */

import android.app.Application;

import com.tsengvn.typekit.Typekit;


public class CustomFont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/bmjua.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/bmjua.ttf"));
    }
}
