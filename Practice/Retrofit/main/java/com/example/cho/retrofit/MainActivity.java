package com.example.cho.retrofit;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {           // Button 누를 시, http://api.github.com/repos/{owners}/{repo}/contributors에서 데이터 받아옴
            @Override
            public void onClick(View v) {
                /*  비동기  */
                MomoService momoService = MomoService.retrofit.create(MomoService.class);
                Call<List<Users>> call = momoService.getUsers("square", "retrofit");    // http://api.github.com/repos/square/retrofit/contributors 정보 Get
                call.enqueue(new Callback<List<Users>>() {
                    @Override
                    public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                        TextView textView = (TextView) findViewById(R.id.test);      // 가져온 정보를 바탕으로 TextView 수정
                        textView.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<List<Users>> call, Throwable t) {
                        Log.e("onFailure", "Fail");
                    }
                });
            }
        });
    }
}