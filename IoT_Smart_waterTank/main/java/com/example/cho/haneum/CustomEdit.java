package com.example.cho.haneum;

import android.content.Context;
import android.content.res.TypedArray;
import android.icu.text.DisplayContext;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Attr;
import org.w3c.dom.Text;

/* 사용자 기입란 Custom View */
public class CustomEdit extends LinearLayout {

    EditText editText;
    TextView textView;

    public CustomEdit(Context context) {
        super(context);
        initView();
    }

    public CustomEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomEdit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infStyle = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(infStyle);
        View v = inflater.inflate(R.layout.activity_custom_edit, this, false);
        addView(v);

        textView = (TextView) findViewById(R.id.edit_text);
        editText = (EditText) findViewById(R.id.edit_ed);
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
        String sText = typedArray.getString(R.styleable.EditAttrs_edit_text);     // TextView Set
        textView.setText(sText);

        String sHint = typedArray.getString(R.styleable.EditAttrs_edit_hint);     // EditView Set
        editText.setHint(sHint);

        int inputType = typedArray.getResourceId(R.styleable.EditAttrs_edit_inputType, 0x00000001);
        editText.setInputType(inputType);

        typedArray.recycle();
    }

    public EditText getEditText() {
        return editText;
    }
}
