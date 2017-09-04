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
     * @param consentName  n번째 콘센트
     * @param consentState n번째 콘센트 On / Off 상태
     * @param listener     Response.Listener (서버로부터 되돌아오는 Response)
     */
    public RegisterRequest(String consentName, boolean consentState,
                           Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("consent", consentName + "");
        parameters.put("status", consentState + "");
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
