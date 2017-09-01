package com.example.cho.multitab;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cho on 2017-09-01.
 */

public class RegisterRequest extends StringRequest {
    private final static String URL = "http://211.253.25.169/jsonInput.php";
    private Map<String, String> parameters;

    /**
     * POST 방식을 통한 매개변수 데이터 전송
     *
     * @param consentState1 1번 콘센트 On/Off 상태
     * @param consentState2 2번 콘센트 On/Off 상태
     * @param consentState3 3번 콘센트 On/Off 상태
     * @param consentState4 4번 콘센트 On/Off 상태
     * @param listener      Response.Listener (서버로부터 되돌아오는 Response)
     */
    public RegisterRequest(boolean consentState1, boolean consentState2, boolean consentState3, boolean consentState4,
                           Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("consent1", consentState1 + "");
        parameters.put("consent2", consentState2 + "");
        parameters.put("consent3", consentState3 + "");
        parameters.put("consent4", consentState4 + "");
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
