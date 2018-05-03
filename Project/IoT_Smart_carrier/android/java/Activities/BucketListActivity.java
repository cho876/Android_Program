package com.example.skcho.smartcarrier.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.skcho.smartcarrier.Api.GoodsResponse;
import com.example.skcho.smartcarrier.ListAdapter;
import com.example.skcho.smartcarrier.ListItem;
import com.example.skcho.smartcarrier.R;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.GoodsEvent;
import com.example.skcho.smartcarrier.Services.Event.GoodsListEvent;
import com.example.skcho.smartcarrier.Services.Service.GoodsService;
import com.example.skcho.smartcarrier.Utils.TransitionsMng;
import com.squareup.otto.Subscribe;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;

public class BucketListActivity extends AppCompatActivity {

    private GoodsService goodsService;

    ArrayList<ListItem> arr_items;
    ListItem goodsItem;
    ListAdapter listAdapter;

    private SharedPreferences pref = null;
    ListView listView;
    String s_user;
    String s_kind;//종류
    String s_name;//이름
    String s_price;//가격
    TextView txt_total;//총합

    int allcount = 0;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        init();
        getGoods();
    }

    private void init() {
        goodsService = new GoodsService();

        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        s_user = pref.getString("Id", "");  // 기존에 저장되어있던 ID 불러와 출력

        listView = (ListView) findViewById(R.id.goods_list);
        txt_total = (TextView) findViewById(R.id.text_total);
        arr_items = new ArrayList<ListItem>();
        listAdapter = new ListAdapter(arr_items, this);
        listView.setAdapter(listAdapter);
    }

    public void getGoods() {
        goodsService.getGoodsById("get", s_user);
    }

    public void deleteGoods(int index) {
        String sIndex = index+"";
        goodsService.deleteGoodsById("delete", s_user, sIndex);
    }

    @Subscribe
    public void onServerEvent(GoodsListEvent goodsListEvent) {
        List<GoodsResponse> goodsResponseList = goodsListEvent.getGoodsResponse();
        putItems(goodsResponseList);
        Toast.makeText(getApplicationContext(), "구매 물품을 불러왔습니다.", Toast.LENGTH_SHORT).show();
        registerForContextMenu(listView);
    }


    @Subscribe
    public void onServerEvent(GoodsEvent goodsEvent) {
        Toast.makeText(getApplicationContext(), "선택 물품을 삭제했습니다.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.menu, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }


    public boolean onContextItemSelected(MenuItem menuitem) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuitem.getMenuInfo();

        index = info.position;
        Log.e("INDEX", index + "");
        switch (menuitem.getItemId()) {
            case R.id.delete:
                goodsItem = arr_items.get(index);
                allcount -= Integer.parseInt(goodsItem.getThird());
                txt_total.setText("총합 : " + allcount);
                arr_items.remove(index);
                listAdapter.notifyDataSetChanged();
                deleteGoods(index+1);
                break;
        }
        return true;
    }


    private void putItems(List<GoodsResponse> data) {
        for (GoodsResponse goodsResponse : data) {
            s_kind = goodsResponse.getGoodsKind();
            s_name = goodsResponse.getGoodsName();
            s_price = goodsResponse.getGoodsPrice();
            allcount += Integer.parseInt(s_price);

            goodsItem = new ListItem(s_kind, s_name, s_price);
            listAdapter.addItem(goodsItem);
        }
        listAdapter.notifyDataSetChanged();
        txt_total.setText("총합: " + allcount);
    }

    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {        // "Back"버튼 두 번 누를 시, 프로그램 종료
        Intent go_main = new Intent(this, MainActivity.class);
        TransitionsMng.UtilClose(go_main);
        startActivity(go_main);
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
