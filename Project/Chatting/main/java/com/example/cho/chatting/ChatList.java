package com.example.cho.chatting;

/**
 * Created by Cho on 2017-07-17.
 */

public class ChatList {
    private int profile;
    private String title, content, time;

    public ChatList(){}

    public ChatList(int profile, String title, String content, String time) {
        this.profile = profile;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public int getProfile() {
        return profile;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}
