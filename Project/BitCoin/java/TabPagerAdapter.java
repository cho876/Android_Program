package com.example.cho.bitcoin;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by Cho on 2017-09-06.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private int tabCount;

    public TabPagerAdapter(Context context, FragmentManager fm, int tabCount) {
        super(fm);
        pref = context.getSharedPreferences("Bitcoin", Context.MODE_PRIVATE);
        editor = pref.edit();
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                editor.putString("target", "bithumb");
                editor.commit();
                BithumbFragment bithumbFragment = new BithumbFragment();
                return bithumbFragment;
            case 1:
                editor.putString("target", "coinone");
                editor.commit();
                CoinoneFragment coinoneFragment = new CoinoneFragment();
                return coinoneFragment;
            case 2:
                editor.putString("target", "korbit");
                editor.commit();
                KorbitFragment korbitFragment = new KorbitFragment();
                return korbitFragment;
            case 3:
                editor.putString("target", "bittrex");
                editor.commit();
                BittrexFragment bittrexFragment = new BittrexFragment();
                return bittrexFragment;
            case 4:
                editor.putString("target", "poloniex");
                editor.commit();
                PoloniexFragment poloniexFragment = new PoloniexFragment();
                return poloniexFragment;
            case 5:
                editor.putString("target", "huobi");
                editor.commit();
                HuobiFragment huobiFragment = new HuobiFragment();
                return huobiFragment;
            default:
                return null;
        }
    }
        @Override
        public int getCount () {
            return tabCount;
        }
    }
