package com.example.cho.haneum;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 수조 상태 UI Custom
 */
public class CustomTable extends LinearLayout {

    private ImageView lv_limg;
    private ImageView lv_rimg;
    private TextView tv_ltitle, tv_lcontents, tv_rtitle, tv_rcontents;

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

        lv_limg = (ImageView) findViewById(R.id.customtable_limg);
        lv_rimg = (ImageView) findViewById(R.id.customtable_rimg);
        tv_ltitle = (TextView) findViewById(R.id.customtable_ltitle);
        tv_rtitle = (TextView) findViewById(R.id.customtable_rtitle);
        tv_lcontents = (TextView) findViewById(R.id.customtable_lcontents);
        tv_rcontents = (TextView) findViewById(R.id.customtable_rcontents);
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
        int limg_res = typedArray.getResourceId(R.styleable.TableAttrs_table_limg, R.drawable.findid);
        lv_limg.setImageResource(limg_res);

        int rimg_res = typedArray.getResourceId(R.styleable.TableAttrs_table_rimg, R.drawable.findid);
        lv_rimg.setImageResource(rimg_res);

        String title_lres = typedArray.getString(R.styleable.TableAttrs_table_left_title);
        tv_ltitle.setText(title_lres);

        String title_rres = typedArray.getString(R.styleable.TableAttrs_table_right_title);
        tv_rtitle.setText(title_rres);

        String contents_lres = typedArray.getString(R.styleable.TableAttrs_table_left_contents);
        tv_lcontents.setText(contents_lres);

        String contents_rres = typedArray.getString(R.styleable.TableAttrs_table_right_contents);
        tv_rcontents.setText(contents_rres);

        typedArray.recycle();
    }

    public TextView getTv_lcontents() {
        return tv_lcontents;
    }

    public TextView getTv_rcontents() {
        return tv_rcontents;
    }

}
