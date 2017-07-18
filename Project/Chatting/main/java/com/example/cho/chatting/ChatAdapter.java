package com.example.cho.chatting;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Cho on 2017-07-12.
 */

public class ChatAdapter extends ArrayAdapter<ChatDTO> {

    private final SimpleDateFormat formatter = new SimpleDateFormat("a h:mm", Locale.getDefault());
    private final static int TYPE_MY = 0;
    private final static int TYPE_ANOTHER = 1;
    private String sEmail;

    public ChatAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void setUserEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        String email = getItem(position).getUserEmail();
        if (!TextUtils.isEmpty(sEmail) && sEmail.equals(email))
            return TYPE_MY;
        return TYPE_ANOTHER;
    }

    private View setAnotherView(LayoutInflater inflater) {
        View convertView = inflater.inflate(R.layout.listitem_another, null);
        ViewAnotherHolder holder = new ViewAnotherHolder();
        holder.bindView(convertView);
        convertView.setTag(holder);
        return convertView;
    }

    private View setMyHolder(LayoutInflater inflater) {
        View convertView = inflater.inflate(R.layout.listitem_my, null);
        ViewMyHolder holder = new ViewMyHolder();
        holder.bindView(convertView);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            if (viewType == TYPE_ANOTHER)
                convertView = setAnotherView(inflater);
            else
                convertView = setMyHolder(inflater);
        }

        if (convertView.getTag() instanceof ViewAnotherHolder) {
            if (viewType != TYPE_ANOTHER)
                convertView = setAnotherView(inflater);
            ((ViewAnotherHolder) convertView.getTag()).setItem(position);
        } else {
            if (viewType != TYPE_MY)
                convertView = setMyHolder(inflater);
            ((ViewMyHolder) convertView.getTag()).setItem(position);
        }
        return convertView;
    }

    private class ViewAnotherHolder {
        private TextView userNick;
        private TextView userContent;
        private TextView userTime;

        private void bindView(View convertView) {
            userNick = (TextView) convertView.findViewById(R.id.listitem_another_nick);
            userContent = (TextView) convertView.findViewById(R.id.listitem_another_content);
            userTime = (TextView) convertView.findViewById(R.id.listitem_another_time);
        }

        private void setItem(int position) {
            ChatDTO chatDTO = getItem(position);
            userNick.setText(chatDTO.getUserNick().toString());
            userContent.setText(chatDTO.getUserContent().toString());
            userTime.setText(formatter.format(chatDTO.getUserTime()));
        }
    }

    private class ViewMyHolder {
        private TextView userContent;
        private TextView userTime;

        private void bindView(View convertView) {
            userContent = (TextView) convertView.findViewById(R.id.listitem_my_content);
            userTime = (TextView) convertView.findViewById(R.id.listitem_my_time);
        }

        private void setItem(int position) {
            ChatDTO chatDTO = getItem(position);
            userContent.setText(chatDTO.getUserContent().toString());
            userTime.setText(formatter.format(chatDTO.getUserTime()));
        }
    }
}