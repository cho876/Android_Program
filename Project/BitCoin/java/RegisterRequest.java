package com.example.cho.bitcoin;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Volley를 활용한 서버 연동 작업을 위한 설정 Class
 */

public class RegisterRequest extends StringRequest {
    private final static String URL = "http://www.moneynet.co.kr/price_client/price_api.php";
    private Map<String, String> parameters;

    /**
     * POST 방식을 통한 매개변수 데이터 전송
     *
     * @param listener     Response.Listener (서버로부터 되돌아오는 Response)
     */
    public RegisterRequest(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
