package com.example.cho.between;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cho on 2017-08-14.
 */

public class Check_Valid {
    private Activity activity;

    public Check_Valid(Activity activity) {
        this.activity = activity;
    }

    /* 이름 유효성 검사 */
    public boolean isValidName(String sName) {
        Pattern p = Pattern.compile(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
        Matcher m = p.matcher(sName);

        /* 한글만 입력 가능 */
        if (!TextUtils.isEmpty(sName) && m.find()) {
            return true;
        } else {
            return false;
        }
    }

    /* 이메일 유효성 검사 */
    public boolean isValidEmail(String sEmail) {
        if (TextUtils.isEmpty(sEmail) || !sEmail.matches(Patterns.EMAIL_ADDRESS.pattern())) {
            Toast.makeText(activity, "이메일 형식이 잘못 되었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /* 비밀번호 유효성 검사 */
    public boolean isValidPw(String sPw) {
        Pattern p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-zA-Z]).*$)");

        Matcher m = p.matcher(sPw);
        /* 최소 6자 이상, 한글 미포함 */
        if (m.find() && !sPw.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            return true;
        } else {
            Toast.makeText(activity, "비밀번호는 영어&숫자 포함\n 6자리 이상입니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
