package com.example.cho.bitcoin;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tsengvn.typekit.TypekitContextWrapper;

public class InformationActivity extends AppCompatActivity {

    private TitlebarCustom titlebarCustom;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initTitlebar();
        initTab();
        initViewPager();
    }

    private void initTab() {
        tabLayout = (TabLayout) findViewById(R.id.info_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(R.color.colorPrimaryDark, R.color.colorPrimaryDark);
        tabLayout.addTab(tabLayout.newTab().setText("빗썸").setIcon(R.drawable.coin_img));
        tabLayout.addTab(tabLayout.newTab().setText("코인원").setIcon(R.drawable.coin_img));
        tabLayout.addTab(tabLayout.newTab().setText("코빗").setIcon(R.drawable.coin_img));
        tabLayout.addTab(tabLayout.newTab().setText("비트렉스").setIcon(R.drawable.coin_img));
        tabLayout.addTab(tabLayout.newTab().setText("폴로닉스").setIcon(R.drawable.coin_img));
        tabLayout.addTab(tabLayout.newTab().setText("후오비").setIcon(R.drawable.coin_img));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTitlebar() {
        titlebarCustom = new TitlebarCustom(this);
        ActionBar actionBar = getSupportActionBar();
        titlebarCustom.setActionBar(actionBar);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
