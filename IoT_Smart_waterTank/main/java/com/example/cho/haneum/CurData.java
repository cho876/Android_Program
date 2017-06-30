package com.example.cho.haneum;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Cho on 2017-06-30.
 */

public class CurData {

    //private Drawable icon;
    private String sTemp, sTurb, sLevel;

    /*public void setIcon(Drawable icon){
        this.icon = icon;
    }

    public Drawable getIcon(){return icon;}
*/
    public void setTemp(String sTemp){
        this.sTemp = sTemp;
    }

    public String getTemp(){
        return sTemp;
    }

    public void setTurb(String sTurb){
        this.sTurb = sTurb;
    }

    public String getTurb(){
        return sTurb;
    }

    public void setLevel(String sLevel){
        this.sLevel = sLevel;
    }

    public String getLevel(){
        return sLevel;
    }
}
