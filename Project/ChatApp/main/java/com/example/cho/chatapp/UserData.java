package com.example.cho.chatapp;

/**
 * Created by Cho on 2017-07-09.
 */

public class UserData {
    private String userEmail, userName, userPw;

    public UserData() {
    }

    public UserData(String userEmail, String userName, String userPw) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPw = userPw;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }
}
