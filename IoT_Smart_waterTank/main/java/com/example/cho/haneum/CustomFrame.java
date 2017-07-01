package com.example.cho.haneum;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class CustomFrame extends LinearLayout {

    private LinearLayout in_bg;
    private LinearLayout out_bg;

    public CustomFrame(Context context){
        super(context);
        initView();
    }

    public CustomFrame(Context context, AttributeSet attrs){
        super(context, attrs);

        initView();
        getAttrs(attrs);
    }

    public CustomFrame(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView(){
        String infStyle = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(infStyle);
        View v = inflater.inflate(R.layout.activity_custom_frame, this, false);
        addView(v);

        in_bg = (LinearLayout)findViewById(R.id.customframe_inside);
        out_bg = (LinearLayout)findViewById(R.id.customframe_outside);
    }

    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FrameAttrs);
        setTypedArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FrameAttrs, defStyle, 0);
        setTypedArray(typedArray);
    }

    private void setTypedArray(TypedArray typedArray) {
        int in_res = typedArray.getResourceId(R.styleable.FrameAttrs_frame_in_bg, R.drawable.round_shape);
        in_bg.setBackgroundResource(in_res);

        int out_res = typedArray.getResourceId(R.styleable.FrameAttrs_frame_out_bg, R.drawable.round_shape_inside);
        out_bg.setBackgroundResource(out_res);

        typedArray.recycle();
    }
}
