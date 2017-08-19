package com.example.cho.between;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Cho on 2017-08-15.
 */

public class Fragment_Info extends Fragment {

    private SharedPreferences pref;
    private TextView tv_name, tv_email, tv_match;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pref = this.getActivity().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.fragment_info, null);
        initView(view);
        setInfo();
        return view;
    }

    private void initView(View view) {
        tv_name = (TextView) view.findViewById(R.id.info_name);
        tv_email = (TextView) view.findViewById(R.id.info_email);
        tv_match = (TextView) view.findViewById(R.id.info_match);
    }

    private void setInfo() {
        String sName = pref.getString("userName", null);
        tv_name.setText("이름: " + sName);
        String sEmail = pref.getString("userEmail", null);
        tv_email.setText("이메일: " + sEmail);
        //String sMatch = pref.getString("userMatch", null);
        //tv_match.setText(sMatch);
    }
}
