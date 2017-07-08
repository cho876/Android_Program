package com.example.cho.account_book;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomEdit extends LinearLayout {

    private TextView title;                 // 사용자 기입란 주제
    private EditText content;               // 사용자 기입란

    public CustomEdit(Context context) {
        super(context);
        initView();
    }

    public CustomEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomEdit(Context context, AttributeSet attrs, int defstyle) {
        super(context, attrs, defstyle);
        initView();
        getAttrs(attrs, defstyle);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_custom_edit, this, false);
        addView(v);

        title = (TextView) findViewById(R.id.custom_edit_title);
        content = (EditText) findViewById(R.id.custom_edit_input);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EditAttrs);
        setTypedArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EditAttrs, defStyle, 0);
        setTypedArray(typedArray);
    }

    private void setTypedArray(TypedArray typedArray) {
        Drawable drawable_res = typedArray.getDrawable(R.styleable.EditAttrs_edit_drawable);   // EditText->drawable Setting
        drawable_res.setBounds(0, 0, 20, 20);
        content.setCompoundDrawables(drawable_res, null, null, null);

        String title_res = typedArray.getString(R.styleable.EditAttrs_edit_title);   // EditText->text Setting
        title.setText(title_res);

        String hint_res = typedArray.getString(R.styleable.EditAttrs_edit_hint);     // EditText->hint Setting
        content.setHint(hint_res);

        typedArray.recycle();
    }
}
