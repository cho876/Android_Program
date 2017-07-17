package com.example.cho.chatting;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by Cho on 2017-07-13.
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
