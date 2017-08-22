package com.example.cho.retrofit;

/**
 * Created by Cho on 2017-08-22.
 */

/*
    받아올 Data class
* */

public class Users {
    private String login;
    private String html_url;
    int contributions;

    @Override
    public String toString() {
        return html_url + "(" + contributions + ")";
    }
}
