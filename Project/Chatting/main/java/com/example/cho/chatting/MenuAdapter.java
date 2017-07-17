package com.example.cho.chatting;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Cho on 2017-07-17.
 */

public class MenuAdapter extends ArrayAdapter<ChatList> {

    public MenuAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_list_item, parent, false);

            CircleImageView profile = (CircleImageView) convertView.findViewById(R.id.menu_list_item_img);
            TextView tv_title = (TextView) convertView.findViewById(R.id.menu_list_item_title);
            TextView tv_content = (TextView) convertView.findViewById(R.id.menu_list_item_content);
            TextView tv_time = (TextView) convertView.findViewById(R.id.menu_list_item_time);

            profile.setImageResource(getItem(position).getProfile());
            tv_title.setText(getItem(position).getTitle());
            tv_content.setText(getItem(position).getContent());
            tv_time.setText(getItem(position).getTime());
        }
        return convertView;
    }
}
