package com.example.cho.bitcoin;

import android.content.Context;
import android.content.res.TypedArray;
import android.icu.text.DisplayContext;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Cho on 2017-09-06.
 */

public class ButtonCustom extends LinearLayout {

    private TextView contents;
    private ImageView symbol;

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
        View view = inflater.inflate(R.layout.custom_button, this, false);
        addView(view);
        contents = (TextView) findViewById(R.id.customButton_contents);
        symbol = (ImageView) findViewById(R.id.customButton_symbol);
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
        contents.setText(sText);

        int iSymbol = typedArray.getResourceId(R.styleable.ButtonCustom_buttonSymbol, 0);
        symbol.setImageResource(iSymbol);

        typedArray.recycle();
    }
}
