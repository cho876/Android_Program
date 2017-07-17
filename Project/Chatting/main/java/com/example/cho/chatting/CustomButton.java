package com.example.cho.chatting;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Attr;

public class CustomButton extends LinearLayout {

    private TextView tv_txt;
    private ImageView iv_symbol;

    public CustomButton(Context context) {
        super(context);
        initView();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_custom_button, this, false);
        addView(view);

        tv_txt = (TextView) view.findViewById(R.id.customButton_txt);
        iv_symbol = (ImageView) view.findViewById(R.id.customButton_img);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomButton);
        setAttrs(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomButton, defStyle, 0);
        setAttrs(typedArray);
    }

    private void setAttrs(TypedArray typedArray) {
        String sText = typedArray.getString(R.styleable.CustomButton_btnAttr_txt);
        tv_txt.setText(sText);

        int symbol = typedArray.getResourceId(R.styleable.CustomButton_btnAttr_img, 0);
        iv_symbol.setImageResource(symbol);

        typedArray.recycle();
    }
}