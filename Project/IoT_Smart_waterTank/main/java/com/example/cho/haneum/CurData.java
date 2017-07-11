package com.example.cho.haneum;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Cho on 2017-06-30.
 */

public class CurData {

    private String sTemp, sTurb, sLevel, sDate;

    public void setTemp(String sTemp) {
        this.sTemp = sTemp;
    }

    public String getTemp() {
        return sTemp;
    }

    public void setTurb(String sTurb) {
        this.sTurb = sTurb;
    }

    public String getTurb() {
        return sTurb;
    }

    public void setLevel(String sLevel) {
        this.sLevel = sLevel;
    }

    public String getLevel() {
        return sLevel;
    }

    public String getDate() {
        return sDate;
    }

    public void setDate(String sDate) {
        this.sDate = sDate;
    }
}
