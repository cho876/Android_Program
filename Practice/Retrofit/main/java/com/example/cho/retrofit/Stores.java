package com.example.cho.retrofit;

/**
 * Json 형태의 데이터 객체화 Getter / Setter
 *
 * @author SungkwonCho
 */
public class Stores {
    private String login;
    private String html_url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    @Override
    public String toString() {
        return login + "(" + html_url + ")";
    }
}
