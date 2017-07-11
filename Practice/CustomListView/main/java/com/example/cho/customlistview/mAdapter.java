package com.example.cho.customlistview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cho on 2017-06-29.
 */

public class mAdapter extends BaseAdapter{

    private ArrayList<MyItem> mItem = new ArrayList<>();   // For ItemSet Array

    @Override
    public int getCount(){            // ArrayList's size
        return mItem.size();
    }

    @Override
    public MyItem getItem(int index){      // index에 해당하는 목록 반환
        return mItem.get(index);
    }

    @Override
    public long getItemId(int index){
        return 0;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup viewGroup){

        Context context = viewGroup.getContext();

        if(convertView == null){   // customListView를 inflate하여 convertView 획득
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, viewGroup, false);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.iv_custom);
        TextView tv_name = (TextView)convertView.findViewById(R.id.name);
        TextView tv_content = (TextView)convertView.findViewById(R.id.contents);

        /*index에 해당하는 MyItem 가져옴 */
        MyItem myItem = getItem(index);

        /* 이를 현재 ListView 내  삽입*/
        imageView.setImageDrawable(myItem.getIcon());
        tv_name.setText(myItem.getName());
        tv_content.setText(myItem.getContents());

        return convertView;
    }

    public void addItem(Drawable drawable, String name, String contents){          // ListView 목록 추가
        MyItem myItem = new MyItem();

        myItem.setIcon(drawable);
        myItem.setName(name);
        myItem.setContents(contents);

        mItem.add(myItem);
    }
}
