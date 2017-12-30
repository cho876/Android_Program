package com.example.skcho.smartcarrier;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;


import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skCho on 2017-12-28.
 *
 */

public class MemoActivity extends AppCompatActivity implements ListInterface, DetailInterface {
    private static final String TAG = "MemoMain";
    ListFragment list;
    DetailFragment detail;

    FrameLayout main;
    FragmentManager manager;

    DBHelper dbHelper;

    List<Memo> datas = new ArrayList<>();
    Dao<Memo, Integer> memoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        list = ListFragment.newInstance(1);
        detail = DetailFragment.newInstance();
        main = (FrameLayout) findViewById(R.id.activity_memo);
        manager = getSupportFragmentManager();
        try {
            loadData();
        } catch (SQLException e) {
        }

        list.setData(datas);
        setList();
    }

    public void loadData() throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        memoDao = dbHelper.getMemoDao();
        datas = memoDao.queryForAll();
    }

    private void setList() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_memo, list);
        transaction.commit();
    }

    @Override
    public void goDetail() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_memo, detail);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void goDetail(int position) {

    }

    @Override
    public void backToList() {
        super.onBackPressed();
    }

    @Override
    public void saveToList(Memo memo) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        memoDao = dbHelper.getMemoDao();
        memoDao.create(memo);
        loadData();
        list.setData(datas);
        super.onBackPressed();
        list.refreshAdapter();
    }

    public void deleteFromList(Memo memo) throws SQLException {
        dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        memoDao = dbHelper.getMemoDao();
        memoDao.delete(memo);
        list.refreshAdapter();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}