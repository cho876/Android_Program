package com.example.cho.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomActivity extends LinearLayout {

    LinearLayout linearLayout;
    ImageView imageView;
    TextView textView;

    public CustomActivity(Context context){
        super(context);
        initView();
    }

    public CustomActivity(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomActivity(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView(){                                         // View initialization
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(infService);
        View v = inflater.inflate(R.layout.activity_custom, this, false);
        addView(v);

        linearLayout = (LinearLayout)findViewById(R.id.activity_custom);
        imageView = (ImageView)findViewById(R.id.image);
        textView = (TextView)findViewById(R.id.text);
    }

    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.customView);

        setTypedArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.customView, defStyle, 0);
        setTypedArray(typedArray);
    }

    private void setTypedArray(TypedArray typedArray){

        int bg_resID = typedArray.getResourceId(R.styleable.customView_bg, 0);        // main.xml에서 app:bg=...로 바꾼 값으로 배경색 재설정
        linearLayout.setBackgroundResource(bg_resID);
        int symbol_resID = typedArray.getResourceId(R.styleable.customView_symbol, R.drawable.join);     // main.xml에서 app:symbol=...로 바꾼 값으로 image 재설정
        imageView.setImageResource(symbol_resID);

        String sText = typedArray.getString(R.styleable.customView_text);       // main.xml에서 app:text=...로 바꾼 값으로 text 재설정
        textView.setText(sText);

        typedArray.recycle();
    }

    public void setBg(int bg_resID){
        linearLayout.setBackgroundColor(bg_resID);
    }

    public void setSymbol(int symbol_resID){
        imageView.setImageResource(symbol_resID);
    }

    public void setTextView(String sText){
        textView.setText(sText);
    }
}
