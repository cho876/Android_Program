package com.example.cho.chatting;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomTitle extends LinearLayout {

    private ImageView iv_symbol;
    private TextView tv_txt;

    public CustomTitle(Context context) {
        super(context);
        initView();
    }

    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomTitle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_custom_title, this, false);
        addView(view);

        iv_symbol = (ImageView) view.findViewById(R.id.customTitle_img);
        tv_txt = (TextView) view.findViewById(R.id.customTitle_txt);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTitle);
        setAttrs(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTitle, defStyle, 0);
        setAttrs(typedArray);
    }

    private void setAttrs(TypedArray typedArray) {
        String sTitle = typedArray.getString(R.styleable.CustomTitle_titleAttr_txt);
        tv_txt.setText(sTitle);

        int img_res = typedArray.getResourceId(R.styleable.CustomTitle_titleAttr_img, 0);
        iv_symbol.setImageResource(img_res);

        typedArray.recycle();
    }
}
