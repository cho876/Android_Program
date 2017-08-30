package com.example.cho.style;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

/**
 * Jsoup을 이용해 파싱을 통한 도서관 열람실 정보 조회 프로그램
 * @author sungkwonCho
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_current_time;  // 현재 시간
    private TextView tv_3_1_total, tv_3_1_current, tv_3_1_rest, tv_3_1_percent;    // 3층 1열람실 정보
    private TextView tv_3_2_total, tv_3_2_current, tv_3_2_rest, tv_3_2_percent;    // 3층 2열람실 정보
    private TextView tv_4_2_total, tv_4_2_current, tv_4_2_rest, tv_4_2_percent;    // 4층 2열람실 정보
    private TextView tv_4_3_total, tv_4_3_current, tv_4_3_rest, tv_4_3_percent;    // 4층 3열람실 정보

    private Button button_3_1_lookup, button_3_1_detail;
    private Button button_3_2_lookup, button_3_2_detail;
    private Button button_4_2_lookup, button_4_2_detail;
    private Button button_4_3_lookup, button_4_3_detail;

    private final String URL_ADDRESS = "http://203.232.237.8/domian5/2/domian5.asp";   // 파싱해올 URL
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getTimeByHTML(tv_current_time);

    }

    private void initView() {
        tv_current_time = (TextView) findViewById(R.id.currentTime);
        tv_3_1_total = (TextView) findViewById(R.id.readingroom_3_1_total);
        tv_3_1_rest = (TextView) findViewById(R.id.readingroom_3_1_rest);
        tv_3_1_current = (TextView) findViewById(R.id.readingroom_3_1_current);
        tv_3_1_percent = (TextView) findViewById(R.id.readingroom_3_1_percent);

        tv_3_2_total = (TextView) findViewById(R.id.readingroom_3_2_total);
        tv_3_2_rest = (TextView) findViewById(R.id.readingroom_3_2_rest);
        tv_3_2_current = (TextView) findViewById(R.id.readingroom_3_2_current);
        tv_3_2_percent = (TextView) findViewById(R.id.readingroom_3_2_percent);

        tv_4_2_total = (TextView) findViewById(R.id.readingroom_4_2_total);
        tv_4_2_rest = (TextView) findViewById(R.id.readingroom_4_2_rest);
        tv_4_2_current = (TextView) findViewById(R.id.readingroom_4_2_current);
        tv_4_2_percent = (TextView) findViewById(R.id.readingroom_4_2_percent);

        tv_4_3_total = (TextView) findViewById(R.id.readingroom_4_3_total);
        tv_4_3_rest = (TextView) findViewById(R.id.readingroom_4_3_rest);
        tv_4_3_current = (TextView) findViewById(R.id.readingroom_4_3_current);
        tv_4_3_percent = (TextView) findViewById(R.id.readingroom_4_3_percent);

        button_3_1_lookup = (Button) findViewById(R.id.readingroom_3_1_lookup_button);
        button_3_1_lookup.setOnClickListener(this);

        button_3_1_detail = (Button) findViewById(R.id.readingroom_3_1_detail_button);
        button_3_1_detail.setOnClickListener(this);

        button_3_2_lookup = (Button) findViewById(R.id.readingroom_3_2_lookup_button);
        button_3_2_lookup.setOnClickListener(this);

        button_3_2_detail = (Button) findViewById(R.id.readingroom_3_2_detail_button);
        button_3_2_detail.setOnClickListener(this);

        button_4_2_lookup = (Button) findViewById(R.id.readingroom_4_2_lookup_button);
        button_4_2_lookup.setOnClickListener(this);

        button_4_2_detail = (Button) findViewById(R.id.readingroom_4_2_detail_button);
        button_4_2_detail.setOnClickListener(this);

        button_4_3_lookup = (Button) findViewById(R.id.readingroom_4_3_lookup_button);
        button_4_3_lookup.setOnClickListener(this);

        button_4_3_detail = (Button) findViewById(R.id.readingroom_4_3_detail_button);
        button_4_3_detail.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.readingroom_3_1_lookup_button:
                getInfoByHTML(2,tv_3_1_total, tv_3_1_current, tv_3_1_rest, tv_3_1_percent);
                break;

            case R.id.readingroom_3_1_detail_button:
                break;

            case R.id.readingroom_3_2_lookup_button:
                getInfoByHTML(3, tv_3_2_total, tv_3_2_current, tv_3_2_rest, tv_3_2_percent);
                break;
            case R.id.readingroom_3_2_detail_button:
                break;

            case R.id.readingroom_4_2_lookup_button:
                getInfoByHTML(4, tv_4_2_total, tv_4_2_current, tv_4_2_rest, tv_4_2_percent);
                break;

            case R.id.readingroom_4_2_detail_button:
                break;

            case R.id.readingroom_4_3_lookup_button:
                getInfoByHTML(5,  tv_4_3_total, tv_4_3_current, tv_4_3_rest, tv_4_3_percent);
                break;
            case R.id.readingroom_4_3_detail_button:
                break;
        }
    }

    /**
     * 현재 시간 조회 기능
     * @param time 현재 시간이 입력될 TextView
     */
    private void getTimeByHTML(final TextView time) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.parse(new URL(URL_ADDRESS).openStream(), "euc-kr", URL_ADDRESS);
                    Element table = doc.select("table").get(1);
                    Element tr = table.select("tr").get(0);
                    final Elements td = tr.select("td");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            time.setText(td.text());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * 각 열람실의 현재 상태 조회
     * @param index     파싱해 가져올 index
     * @param total     총 좌석
     * @param current   현재 예약된 좌석
     * @param rest      남은 좌석
     * @param percent   좌석 예약률
     */
    private void getInfoByHTML(final int index, final TextView total, final TextView current, final TextView rest, final TextView percent) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.parse(new URL(URL_ADDRESS).openStream(), "euc-kr", URL_ADDRESS);
                    Element table = doc.select("table").get(1);
                    Element tr = table.select("tr").get(index);
                    final Elements tds = tr.select("td");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            total.setText(tds.get(2).text());
                            current.setText(tds.get(3).text());
                            rest.setText(tds.get(4).text());
                            percent.setText(tds.get(5).text());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
