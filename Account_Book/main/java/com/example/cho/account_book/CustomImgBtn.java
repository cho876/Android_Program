package com.example.cho.account_book;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class CustomImgBtn extends LinearLayout {

    private ImageButton symbol;
    private TextView title;

    public CustomImgBtn(Context context) {
        super(context);
        initView();
    }

    public CustomImgBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomImgBtn(Context context, AttributeSet attrs, int defstyle) {
        super(context, attrs, defstyle);
        initView();
        getAttrs(attrs, defstyle);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_custom_img_btn, this, false);
        addView(v);

        title = (TextView) findViewById(R.id.customimgbtn_title);
        symbol = (ImageButton) findViewById(R.id.customimgbtn_btn);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImgbtnAttrs);
        setTypedArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImgbtnAttrs, defStyle, 0);
        setTypedArray(typedArray);
    }

    private void setTypedArray(TypedArray typedArray) {
        int symbol_res = typedArray.getResourceId(R.styleable.ImgbtnAttrs_imgbtn_symbol, R.drawable.vec_pw);
        symbol.setImageResource(symbol_res);

        String title_res = typedArray.getString(R.styleable.ImgbtnAttrs_imgbtn_title);   // EditText->text Setting
        title.setText(title_res);

        typedArray.recycle();
    }

    public CustomImgBtn getlBtn() {
        return this;
    }


}
