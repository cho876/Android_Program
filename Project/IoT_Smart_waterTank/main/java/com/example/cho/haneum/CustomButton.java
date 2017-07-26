package com.example.cho.haneum;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/* 하단부 버튼 Custom View */
public class CustomButton extends LinearLayout {

    Button leftBtn;
    Button rightBtn;

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
        String infStyle = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(infStyle);
        View v = inflater.inflate(R.layout.activity_custom_button, this, false);
        addView(v);
        leftBtn = (Button) findViewById(R.id.button_left);
        rightBtn = (Button) findViewById(R.id.button_right);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BtnAttrs);
        setTypedArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BtnAttrs, defStyle, 0);
        setTypedArray(typedArray);
    }

    private void setTypedArray(TypedArray typedArray) {
        String sLeft = typedArray.getString(R.styleable.BtnAttrs_btn_left_text);          // Left Button Set
        leftBtn.setText(sLeft);

        String sRight = typedArray.getString(R.styleable.BtnAttrs_btn_right_text);        // Right Button Set
        rightBtn.setText(sRight);

        Drawable dLeftBtn = typedArray.getDrawable(R.styleable.BtnAttrs_btn_left_img);    // Left Button Drawable Set
        dLeftBtn.setBounds(0, 0, 60, 60);
        leftBtn.setCompoundDrawables(dLeftBtn, null, null, null);

        Drawable dRightBtn = typedArray.getDrawable(R.styleable.BtnAttrs_btn_right_img);  // Right Button Drawable Set
        dRightBtn.setBounds(0, 0, 60, 60);
        rightBtn.setCompoundDrawables(dRightBtn, null, null, null);

        typedArray.recycle();
    }

    public Button getLeftBtn() {
        return leftBtn;
    }

    public Button getRightBtn() {
        return rightBtn;
    }
}
