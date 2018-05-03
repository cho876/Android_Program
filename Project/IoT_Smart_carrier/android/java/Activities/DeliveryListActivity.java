package com.example.skcho.smartcarrier.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.skcho.smartcarrier.Api.DeliverResponse;
import com.example.skcho.smartcarrier.CouponListAdapter;
import com.example.skcho.smartcarrier.ListAdapter;
import com.example.skcho.smartcarrier.ListItem;
import com.example.skcho.smartcarrier.R;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.DeliveryEvent;
import com.example.skcho.smartcarrier.Services.Event.DeliveryListEvent;
import com.example.skcho.smartcarrier.Services.Service.GoodsService;
import com.squareup.otto.Subscribe;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;

public class DeliveryListActivity extends AppCompatActivity {

    private GoodsService goodsService;

    ArrayList<ListItem> arr_items;
    ListItem couponListItem;
    ListAdapter couponListAdapter;
    //CouponListAdapter couponListAdapter;

    private SharedPreferences pref = null;
    ListView listView;
    String s_id, s_name, s_address;
    ImageView img_flag;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list);

        init();
        deliverByPost();
    }

    private void init() {
        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        s_id = pref.getString("Id", "");  // 기존에 저장되어있던 ID 불러와 출력

        img_flag = (ImageView) findViewById(R.id.glv_flag);
        goodsService = new GoodsService();

        listView = (ListView) findViewById(R.id.deliverylist_list);
        arr_items = new ArrayList<ListItem>();
        couponListAdapter = new ListAdapter(arr_items, this);
        //couponListAdapter = new CouponListAdapter(arr_items, this);
        listView.setAdapter(couponListAdapter);
    }

    public void deliverByPost() {
        goodsService.allDeliveryList("all");
    }

    public void serviceByPost() {
        goodsService.serviceDeliverById("service", s_id);
    }

    @Subscribe
    public void onServerEvent(DeliveryListEvent deliveryListEvent) {
        List<DeliverResponse> deliverResponseList = deliveryListEvent.getDeliverResponseList();
        putItems(deliverResponseList);
        Toast.makeText(this, "배송 리스트를 불러왔습니다.", Toast.LENGTH_SHORT).show();
        registerForContextMenu(listView);
    }

    @Subscribe
    public void onServerEvent(DeliveryEvent deliveryEvent) {
        Toast.makeText(this, "배송 서비스를 승락했습니다.", Toast.LENGTH_SHORT).show();
    }

    private void putItems(List<DeliverResponse> data) {
        for (DeliverResponse deliverResponse : data) {
            s_name = deliverResponse.getUsername();
            s_address = deliverResponse.getAddress();
            if (deliverResponse.getFlag() == 1)
                img_flag.setImageResource(R.drawable.fill_light_vec);

            couponListItem = new ListItem(s_id, s_name, s_address);
            couponListAdapter.addItem(couponListItem);
        }
        couponListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.deliver, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }


    public boolean onContextItemSelected(MenuItem menuitem) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuitem.getMenuInfo();

        index = info.position;
        Log.e("INDEX", index + "");

        switch (menuitem.getItemId()) {
            case R.id.send:
                img_flag = (ImageView) findViewById(R.id.glv_flag);
                img_flag.setImageResource(R.drawable.fill_light_vec);
                couponListItem = arr_items.get(index);
                couponListAdapter.notifyDataSetChanged();
                serviceByPost();
        }
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
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
