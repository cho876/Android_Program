package com.example.cho.chatting;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomEditText extends LinearLayout {

    private TextView tv_txt;
    private EditText ed_content;

    public CustomEditText(Context context) {
        super(context);
        initView();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_custom_edit_text, this, false);
        addView(view);

        tv_txt = (TextView) view.findViewById(R.id.customEdit_txt);
        ed_content = (EditText) view.findViewById(R.id.customEdit_edit);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        setAttrs(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText, defStyle, 0);
        setAttrs(typedArray);
    }

    private void setAttrs(TypedArray typedArray) {
        /*  TextView Set  */
        String sTitle = typedArray.getString(R.styleable.CustomEditText_editAttr_txt);
        tv_txt.setText(sTitle);

        /*  EditText hint Set  */
        String sHint = typedArray.getString(R.styleable.CustomEditText_editAttr_edit_hint);
        ed_content.setHint(sHint);

        /*  EditText inpuType Set */
        int inputType = typedArray.getInt(R.styleable.CustomEditText_editAttr_edit_inputType, 0);
        ed_content.setInputType(inputType);

        /*  EditText 내부 왼편 이미지 Set */
        Drawable drawable = typedArray.getDrawable(R.styleable.CustomEditText_editAttr_edit_drawable);
        drawable.setBounds(0, 0, 60, 60);
        ed_content.setCompoundDrawables(drawable, null, null, null);

        typedArray.recycle();
    }

    public EditText getEd_content(){
        return ed_content;
    }
}
