package com.example.cho.bitcoin;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by Cho on 2017-09-16.
 */

public class Data {

    private JSONObject jsonObject;

    private String exchange;
    private String koreaMax;
    private String koreaMin;
    private String usaMax;
    private String usaMin;
    private String koreaChangeRate;
    private String usaChangeRate;
    private String percent;
    private String premiumPercent;
    private String koreaPremium;
    private String usaPremium;
    private String volume;
    private DecimalFormat df;

    public Data(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        df = new DecimalFormat("###,###.####");

        try {
            setExchange(jsonObject.getString("exchange"));
            double koreaLast = Double.parseDouble(jsonObject.getString("krw_last"));
            setKoreaMax(koreaLast);
            double koreaLow = Double.parseDouble(jsonObject.getString("krw_low"));
            setKoreaMin(koreaLow);
            double usaLast = Double.parseDouble(jsonObject.getString("usd_last"));
            setUsaMax(usaLast);
            double usaLow = Double.parseDouble(jsonObject.getString("usd_low"));
            setUsaMin(usaLow);
            double percent = Double.parseDouble(jsonObject.getString("percent"));
            setPercent(percent);
            double premiumPercent = Double.parseDouble(jsonObject.getString("premium_percent"));
            setPremiumPercent(premiumPercent);
            double koreaPremium = Double.parseDouble(jsonObject.getString("krw_premium"));
            setKoreaPremium(koreaPremium);
            double usaPremium = Double.parseDouble(jsonObject.getString("usd_premium"));
            setUsaPremium(usaPremium);
            double volume = Double.parseDouble(jsonObject.getString("volume"));
            setVolume(volume);
            double koreaFirst = Double.parseDouble(jsonObject.getString("krw_first"));
            setKoreaChangeRate(koreaLast, koreaFirst);
            double usaFirst = Double.parseDouble(jsonObject.getString("usd_first"));
            setUsaChangeRate(usaLast, usaFirst);
        } catch (JSONException e) {
        }
    }

    public void setUsaMin(double usaMin) {
        this.usaMin = df.format(usaMin);
    }

    public void setUsaMax(double usaMax) {
        this.usaMax = df.format(usaMax);
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;                // 거래소 명
    }

    public void setVolume(double volume) {
        this.volume = df.format(volume);
    }

    public void setUsaPremium(double usaPremium) {
        this.usaPremium = df.format(usaPremium);
    }

    public void setKoreaChangeRate(double koreaLast, double koreaFirst) {
        this.koreaChangeRate = String.format("%.2f", koreaLast - koreaFirst);     // 한국 변동 률
    }

    public void setKoreaMax(double koreaMax) {
        this.koreaMax = df.format(koreaMax);
    }

    public void setKoreaMin(double koreaMin) {
        this.koreaMin = df.format(koreaMin);
    }

    public void setKoreaPremium(double koreaPremium) {
        this.koreaPremium = df.format(koreaPremium);
    }

    public void setPercent(double percent) {
        this.percent = df.format(percent);
    }

    public void setPremiumPercent(double premiumPercent) {
        this.premiumPercent = df.format(premiumPercent);
    }

    public void setUsaChangeRate(double usaMax, double usaMin) {
        this.usaChangeRate = String.format("%.2f", usaMax - usaMin);           // 미국 변동 률
    }

    public String getExchange() {
        return exchange;
    }

    public String getKoreaChangeRate() {
        return koreaChangeRate;
    }

    public String getKoreaMax() {
        return koreaMax;
    }

    public String getKoreaMin() {
        return koreaMin;
    }

    public String getPercent() {
        return percent;
    }

    public String getPremiumPercent() {
        return premiumPercent;
    }

    public String getUsaChangeRate() {
        return usaChangeRate;
    }

    public String getUsaMax() {
        return usaMax;
    }

    public String getUsaMin() {
        return usaMin;
    }

    public String getKoreaPremium() {
        return koreaPremium;
    }

    public String getUsaPremium() {
        return usaPremium;
    }

    public String getVolume() {
        return volume;
    }

}
