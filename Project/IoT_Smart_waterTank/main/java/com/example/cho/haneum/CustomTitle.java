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

/**
 * 상단부 Custom Title
 */
public class CustomTitle extends LinearLayout {

    ImageView iv_img;
    TextView tv_content;

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
        String infStyle = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(infStyle);
        View v = layoutInflater.inflate(R.layout.activity_custom_title, this, false);
        addView(v);

        iv_img = (ImageView) findViewById(R.id.title_symbol);
        tv_content = (TextView) findViewById(R.id.title_text);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleAttrs);
        setTypedArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleAttrs, defStyle, 0);
        setTypedArray(typedArray);
    }

    private void setTypedArray(TypedArray typedArray) {
        int symbol_ref = typedArray.getResourceId(R.styleable.TitleAttrs_title_symbol, R.drawable.findid);
        iv_img.setImageResource(symbol_ref);

        String sText = typedArray.getString(R.styleable.TitleAttrs_title_text);
        tv_content.setText(sText);

        typedArray.recycle();
    }

    public ImageView getIv_img() {
        return iv_img;
    }

    public TextView getTv_content() {
        return tv_content;
    }
}
