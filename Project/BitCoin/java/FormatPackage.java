package com.example.cho.bitcoin;

import android.widget.TextView;

/**
 * Created by Cho on 2017-09-16.
 */

public class FormatPackage {

    /**
     * Format이 필요없는 기본 Setter
     *
     * @param tv       거래소 명 TextView
     * @param exchange 받아온 거래소 명
     */
    public void setText(TextView tv, String exchange) {
        tv.setText(exchange);
    }

    /**
     * 변동액 퍼센티지(%) Setter
     *
     * @param tv      변동액 퍼센티지(%) TextView
     * @param percent 받아온 변동액 퍼센티지(%)
     */
    public void setTextPercent(TextView tv, String percent) {
        tv.setText("(" + percent + "%)");
    }

    /**
     * 한국 변동 액 Setter
     *
     * @param tv   변동액 TextVie
     * @param rate 변동액 데이터
     */
    public void setTextKorea(TextView tv, String rate) {
        tv.setText(rate + "(원)");
    }

    /**
     * 미국 변동 액 Setter
     *
     * @param tv   변동액 TextView
     * @param rate 변동액 데이터
     */
    public void setTextUsa(TextView tv, String rate) {
        tv.setText(rate + "($)");
    }


    /**
     * 총 거래량 Setter
     *
     * @param tv     총 거래량 TextView
     * @param volume 받아온 총 거래량
     */
    public void setTextVolume(TextView tv, String volume) {
        tv.setText("총 거래량 : " + volume);
    }
}
