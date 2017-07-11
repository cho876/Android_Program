package com.example.cho.haneum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Cho on 2017-06-30.
 */

public class CustomAdapter extends BaseAdapter {

    private static ArrayList<CurData> mData = new ArrayList<>();

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CurData getItem(int index) {
        return mData.get(index);
    }

    @Override
    public long getItemId(int index) {
        return 0;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_custom_list_view, viewGroup, false);
        }

        TextView tvTemp = (TextView) convertView.findViewById(R.id.customlist_temp);
        TextView tvTurb = (TextView) convertView.findViewById(R.id.customlist_turb);
        TextView tvLevel = (TextView) convertView.findViewById(R.id.customlist_level);
        TextView tvDate = (TextView) convertView.findViewById(R.id.customlist_time);

        CurData curData = getItem(index);

        tvTemp.setText(curData.getTemp());
        tvTurb.setText(curData.getTurb());
        tvLevel.setText(curData.getLevel());
        tvDate.setText(curData.getDate());

        return convertView;
    }

    public void addItem(String temp, String turb, String level, String date) {
        CurData curData = new CurData();

        curData.setTemp(temp);
        curData.setTurb(turb);
        curData.setLevel(level);
        curData.setDate(date);

        mData.add(curData);
    }

    public void deleteItem(int index) {
        mData.remove(index);
        this.notifyDataSetChanged();
    }

}
