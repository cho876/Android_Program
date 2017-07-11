package com.example.cho.chatapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Cho on 2017-07-09.
 */

public class ChatAdapter extends ArrayAdapter<ChatData> {

    private final SimpleDateFormat format = new SimpleDateFormat("a h:mm", Locale.getDefault());

    public ChatAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listitem, null);

            holder = new ViewHolder();
            holder.txtUserName = (TextView) convertView.findViewById(R.id.item_userName);
            holder.txtMsg = (TextView) convertView.findViewById(R.id.item_Msg);
            holder.txtTime = (TextView) convertView.findViewById(R.id.item_Time);
            Log.e("ADAPTER",holder.txtMsg.getText().toString());

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        ChatData chatData = getItem(position);
        holder.txtUserName.setText(chatData.userEmail);
        holder.txtMsg.setText(chatData.Msg);
        holder.txtTime.setText(format.format(chatData.time));
        Log.e("ADAPTER",holder.txtMsg.getText().toString());

        return convertView;
    }

    private class ViewHolder {
        private TextView txtUserName;
        private TextView txtMsg;
        private TextView txtTime;
    }
}