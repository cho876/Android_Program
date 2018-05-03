package com.example.skcho.smartcarrier.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.skcho.smartcarrier.Api.CouponResponse;
import com.example.skcho.smartcarrier.CouponListAdapter;
import com.example.skcho.smartcarrier.ListAdapter;
import com.example.skcho.smartcarrier.ListItem;
import com.example.skcho.smartcarrier.R;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.CouponEvent;
import com.example.skcho.smartcarrier.Services.Event.CouponListEvent;
import com.example.skcho.smartcarrier.Services.Service.CouponService;
import com.squareup.otto.Subscribe;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;

public class CouponListActivity extends AppCompatActivity {

    private CouponService couponService;

    ArrayList<ListItem> arr_items;
    ListItem couponListItem;
    ListAdapter couponListAdapter;
    //CouponListAdapter couponListAdapter;

    private SharedPreferences pref = null;
    ListView listView;
    String s_user;
    String s_index, s_kind, s_desc; // 인덱스, 종류, 세부사항
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);

        init();
        getCouponById();
        //CouponDB coupon_db = new CouponDB(this, this, couponListAdapter, couponListItem, listView, s_user);
        //coupon_db.execute(s_user, "get");
    }

    private void init() {
        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        s_user = pref.getString("Id", "");  // 기존에 저장되어있던 ID 불러와 출력

        couponService = new CouponService();

        listView = (ListView) findViewById(R.id.coupon_list);
        arr_items = new ArrayList<ListItem>();
        couponListAdapter = new ListAdapter(arr_items, this);
        //couponListAdapter = new CouponListAdapter(arr_items, this);
        listView.setAdapter(couponListAdapter);
    }

    public void getCouponById() {
        couponService.getCouponById(s_user);
    }

    public void useCouponById(int index) {
        couponService.useCouponById(s_user, index+"");
    }

    @Subscribe
    public void onServerEvent(CouponListEvent couponListEvent) {
        List<CouponResponse> couponResponseList = couponListEvent.getCouponResponseList();
        putItems(couponResponseList);
        Toast.makeText(this, "쿠폰 목록을 불러왔습니다.", Toast.LENGTH_SHORT).show();
        registerForContextMenu(listView);
    }

    @Subscribe
    public void onServerEvent(CouponEvent couponEvent) {
        Toast.makeText(this, "쿠폰을 사용했습니다.", Toast.LENGTH_SHORT).show();
    }

    private void putItems( List<CouponResponse> data) {
        int index = 1;
        for (CouponResponse couponResponse : data) {
            s_index = String.valueOf(index);
            s_kind = couponResponse.getKind();
            s_desc = couponResponse.getDescription();

            couponListItem = new ListItem(s_index, s_kind, s_desc);
            couponListAdapter.addItem(couponListItem);
        }
        couponListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }


    public boolean onContextItemSelected(MenuItem menuitem) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuitem.getMenuInfo();

        index = info.position;
        Log.e("INDEX", index + "");

        couponListItem = arr_items.get(index);
        arr_items.remove(index);
        couponListAdapter.notifyDataSetChanged();
        useCouponById(index+1);
        //CouponDB.Delete_DB deleteDB = new CouponDB.Delete_DB(this);
        //deleteDB.execute(s_user, "use");

        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}
