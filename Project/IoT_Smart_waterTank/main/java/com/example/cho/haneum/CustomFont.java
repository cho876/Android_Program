package com.example.cho.haneum;

import android.app.Application;
import android.graphics.Typeface;

import com.tsengvn.typekit.Typekit;

/**
 * Custom Font
 */

/* App Custom Font 지정 */
public class CustomFont extends Application {            // custom 폰트 지정
    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/bmjua.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/bmjua.ttf"));
    }
}
