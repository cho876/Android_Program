package com.example.cho.account_book;

import android.content.Context;
import android.content.res.TypedArray;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomTitle extends LinearLayout {

    private ImageView symbol;
    private TextView title;

    public CustomTitle(Context context){
        super(context);
        initView();
    }

    public CustomTitle(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomTitle(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initView();
        getAttrs(attrs, defStyle);
    }

    public void initView(){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_custom_title, this, false);
        addView(v);

        symbol = (ImageView)findViewById(R.id.customtitle_symbol);
        title = (TextView) findViewById(R.id.customtitle_title);
    }

    public void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleAttrs);
        setTypedArray(typedArray);
    }

    public void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleAttrs, defStyle, 0);
        setTypedArray(typedArray);
    }

    public void setTypedArray(TypedArray typedArray){
        int symbol_res = typedArray.getResourceId(R.styleable.TitleAttrs_title_symbol, R.drawable.vec_join);
        symbol.setImageResource(symbol_res);

        String sTitle = typedArray.getString(R.styleable.TitleAttrs_title_title);
        title.setText(sTitle);

        typedArray.recycle();
    }
}
