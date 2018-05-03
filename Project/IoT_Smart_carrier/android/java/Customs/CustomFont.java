package com.example.skcho.smartcarrier.Customs;

/**
 * Created by skCho on 2017-01-07.
 * <p>
 * Set custom font
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
