package com.example.cho.multitab;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Cho on 2017-09-02.
 */

public class ActionBarCustom {
    private Context context;

    public ActionBarCustom(Context context){
        this.context = context;
    }

    public void setActionBar(ActionBar actionBar) {
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View customView = LayoutInflater.from(context).inflate(R.layout.custom_bar, null, false);
        actionBar.setCustomView(customView);

        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams
                (ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(customView, params);

    }
}
