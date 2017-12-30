package com.example.skcho.smartcarrier;

/**
 * Created by skCho on 2017-12-28.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;
import java.util.Date;

public class DetailFragment extends Fragment implements View.OnClickListener {

    Context context = null;
    DetailInterface detailInterface = null;

    View view = null;

    Button btnSave, btnCancel;
    EditText editMemo;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_detail, container, false);
            btnSave = (Button) view.findViewById(R.id.btnSave);
            btnCancel = (Button) view.findViewById(R.id.btnCancel);
            editMemo = (EditText) view.findViewById(R.id.editMemo);

            btnSave.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.detailInterface = (DetailInterface) context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                try {
                    Memo memo = new Memo();
                    String string = editMemo.getText().toString();
                    memo.setMemo(string);
                    memo.setDate(new Date(System.currentTimeMillis()));

                    detailInterface.saveToList(memo);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnCancel:
                detailInterface.backToList();
                break;
        }
    }

}
