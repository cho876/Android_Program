package com.example.skcho.smartcarrier;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Cho on 2017-12-29.
 */

public class FabUtils {
    private Context context;
    private FloatingActionButton fb_on, fb_memo, fb_news;
    private Animation fabOpen, fabClose, fabClockwise, fabAntiClockwise;
    boolean isOpen = false;

    public FabUtils(Context context, FloatingActionButton fb_on, FloatingActionButton fb_memo, FloatingActionButton fb_news, boolean isOpen) {
        this.context = context;
        this.fb_on = fb_on;
        this.fb_memo = fb_memo;
        this.fb_news = fb_news;
        this.isOpen = isOpen;

        fabOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(context, R.anim.fab_close);
        fabClockwise = AnimationUtils.loadAnimation(context, R.anim.rotate_clockwise);
        fabAntiClockwise = AnimationUtils.loadAnimation(context, R.anim.rotate_anticlockwise);
    }

    public void clickFab() {
        fb_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    fb_news.startAnimation(fabClose);
                    fb_memo.startAnimation(fabClose);
                    fb_on.startAnimation(fabAntiClockwise);
                    isOpen = false;
                } else {
                    fb_news.startAnimation(fabOpen);
                    fb_memo.startAnimation(fabOpen);
                    fb_on.startAnimation(fabClockwise);
                    fb_news.setClickable(true);
                    fb_memo.setClickable(true);

                    fb_news.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent go_news = new Intent(context, MemoActivity.class);
                            context.startActivity(go_news);
                        }
                    });
                    fb_memo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent go_main = new Intent(context, MemoActivity.class);
                            context.startActivity(go_main);
                        }
                    });
                    isOpen = true;
                }
            }
        });
    }
}