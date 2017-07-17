package com.example.cho.chatting;

/**
 * Created by Cho on 2017-07-12.
 */

public class ChatDTO {
    private String userName;
    private String userNick;
    private String userEmail;
    private String userContent;
    private long userTime;

    public ChatDTO() {
    }

    public ChatDTO(String userName, String userNick, String userEmail, String userContent, long userTime) {
        this.userName = userName;
        this.userNick = userNick;
        this.userEmail = userEmail;
        this.userContent = userContent;
        this.userTime = userTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserContent() {
        return userContent;
    }

    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }

    public long getUserTime() {
        return userTime;
    }

    public void setUserTime(long userTime) {
        this.userTime = userTime;
    }
}
