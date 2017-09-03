package com.example.cho.multitab;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference를 통한 Data Getter / Setter
 */

public class SharedPreferenceGetSet {

    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SharedPreferenceGetSet(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("consentData", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setPrefInt(String prefName, int data) {
        editor.putInt(prefName, data);
        editor.commit();
    }

    public int getPrefInt(String prefName) {
        return pref.getInt(prefName, 0);
    }

    public void setPrefString(String prefName, String data) {
        editor.putString(prefName, data);
        editor.commit();
    }

    public String getPrefString(String prefName) {
        return pref.getString(prefName, "오전");
    }

    public void setPrefBoolean(String prefName, Boolean data) {
        editor.putBoolean(prefName, data);
        editor.commit();
    }

    public Boolean getPrefBoolean(String prefName) {
        return pref.getBoolean(prefName, false);
    }
}
