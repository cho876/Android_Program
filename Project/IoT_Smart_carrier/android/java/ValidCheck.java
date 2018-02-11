package com.example.skcho.smartcarrier;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 사용자 기입란 유효성 검사
 */

public class ValidCheck {

    private Activity activity;

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

    /*  아이디 유효성 검사  */
    public boolean isValidId(String sId) {
        if (TextUtils.isEmpty(sId) || sId == null || sId.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            Toast.makeText(activity, "아이디가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*  이메일 유효성 검사  */
    public boolean isValidEmail(String sEmail) {
        if (TextUtils.isEmpty(sEmail) || !sEmail.matches(Patterns.EMAIL_ADDRESS.pattern())) {
            Toast.makeText(activity, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(activity, "비밀번호는 영어&숫자 포함\n 6자리 이상입니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /*  희망 탁도 유효성 검사  */
    public boolean isValidTurb(String sTurb) {
        if (sTurb.equals("0.00") || sTurb.equals("1.00") || sTurb.equals("2.00") || sTurb.equals("3.00") || sTurb.equals("4.00"))
            return true;
        else {
            Toast.makeText(activity, "탁도 값이 잘못 되었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /*  비밀번호 동일 검사  */
    public boolean isSamePw(String sPw, String sPwchk) {
        if (sPw.equals(sPwchk))
            return true;
        else {
            Toast.makeText(activity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /*      기입란 모두 작성 완료여부 확인   */
    public boolean isWrited(String... str) {
        for (String edit : str) {
            if (edit.equals("")) {
                Toast.makeText(activity, "공백란이 존재합니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}