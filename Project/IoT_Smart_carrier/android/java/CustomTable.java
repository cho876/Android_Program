package com.example.skcho.smartcarrier;

/**
 * Created by skCho on 2017-01-07.
 * 
 * Set custom table frame
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomTable extends LinearLayout {

    private ImageView lv_img;
    private TextView tv_title, tv_contents;

    public CustomTable(Context context) {
        super(context);
        initView();
    }

    public CustomTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomTable(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infStyle = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(infStyle);
        View v = inflater.inflate(R.layout.activity_custom_table, this, false);

        addView(v);

        lv_img = (ImageView) findViewById(R.id.customtable_img);
        tv_title = (TextView) findViewById(R.id.customtable_title);
        tv_contents = (TextView) findViewById(R.id.customtable_contents);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TableAttrs);
        setTypedArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TableAttrs, defStyle, 0);
        setTypedArray(typedArray);
    }

    private void setTypedArray(TypedArray typedArray) {
        int img_res = typedArray.getResourceId(R.styleable.TableAttrs_table_img, R.drawable.weight_img);
        lv_img.setImageResource(img_res);

        String title_res = typedArray.getString(R.styleable.TableAttrs_table_title);
        tv_title.setText(title_res);

        String contents_lres = typedArray.getString(R.styleable.TableAttrs_table_contents);
        tv_contents.setText(contents_lres);

        typedArray.recycle();
    }

    public TextView getTv_contents() {
        return tv_contents;
    }
}
