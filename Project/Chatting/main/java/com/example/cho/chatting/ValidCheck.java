package com.example.cho.chatting;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cho on 2017-07-12.
 */

public class ValidCheck {

    private Activity activity;

    public ValidCheck() {
    }

    public ValidCheck(Activity activity) {
        this.activity = activity;
    }

    /*  이름 유효성 검사  */
    public boolean isValidName(String sName) {
        if (TextUtils.isEmpty(sName) || sName == null) {
            Toast.makeText(activity, "이름이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*  이메일 유효성 검사  */
    public boolean isValidEmail(String sEmail) {
        if (TextUtils.isEmpty(sEmail) || !sEmail.matches(Patterns.EMAIL_ADDRESS.pattern())) {
            Toast.makeText(activity, "이메일이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*  비밀번호 유효성 검사  */
    public boolean isValidPw(String sPw) {
        Pattern p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-zA-Z]).*$)");

        Matcher m = p.matcher(sPw);
            /*      최소 6자 이상, 한글 미포함       */
        if (m.find() && !sPw.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            return true;
        } else {
            Toast.makeText(activity, "비밀번호 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /*  비밀번호 동일 검사  */
    public boolean isSamePw(String sPw, String sPwchk){
        if(sPw.equals(sPwchk))
            return true;
        else {
            Toast.makeText(activity, "비밀번호 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
