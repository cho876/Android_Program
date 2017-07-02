package com.example.cho.haneum;

import android.content.Context;
import android.content.res.TypedArray;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Attr;

public class CustomTitle extends LinearLayout {

    ImageView symbol;
    TextView textView;

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

    private void initView(){
        String infStyle = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(infStyle);
        View v = layoutInflater.inflate(R.layout.activity_custom_title, this, false);
        addView(v);

        symbol = (ImageView)findViewById(R.id.title_symbol);
        textView = (TextView)findViewById(R.id.title_text);
    }

    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleAttrs);
        setTypedArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleAttrs, defStyle, 0);
        setTypedArray(typedArray);
    }

    private void setTypedArray(TypedArray typedArray){
        int symbol_ref = typedArray.getResourceId(R.styleable.TitleAttrs_title_symbol, R.drawable.findid);
        symbol.setImageResource(symbol_ref);

        String sText = typedArray.getString(R.styleable.TitleAttrs_title_text);
        textView.setText(sText);

        typedArray.recycle();
    }

    public TextView getTitle(){return textView;}
}
