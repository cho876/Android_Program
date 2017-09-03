package com.example.cho.multitab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.icu.text.DisplayContext;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Attr;
import org.w3c.dom.Text;

/**
 * CustomButton
 */
public class ButtonCustom extends LinearLayout {

    private TextView customBtn_text;
    private ImageView custonBtn_img;

    public ButtonCustom(Context context) {
        super(context);
        initView();
    }

    public ButtonCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public ButtonCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_button_custom, this, false);
        addView(view);
        customBtn_text = (TextView) findViewById(R.id.custom_btn_img_text);
        custonBtn_img = (ImageView) findViewById(R.id.custom_btn_img);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ButtonCustom);
        setTypedArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ButtonCustom, defStyle, 0);
        setTypedArray(typedArray);
    }

    private void setTypedArray(TypedArray typedArray) {
        String sText = typedArray.getString(R.styleable.ButtonCustom_buttonText);
        customBtn_text.setText(sText);

        int dSymbol = typedArray.getResourceId(R.styleable.ButtonCustom_buttonSymbol, R.drawable.vec_on);
        custonBtn_img.setImageResource(dSymbol);

        typedArray.recycle();
    }
}
