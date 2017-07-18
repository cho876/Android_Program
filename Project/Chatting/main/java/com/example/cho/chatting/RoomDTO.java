package com.example.cho.chatting;

/**
 * Created by Cho on 2017-07-17.
 */

public class RoomDTO {
    private int roomImg;
    private String roomTitle, roomContent, roomTime;

    public RoomDTO() {
    }

    public RoomDTO(int roomImg, String roomTitle, String roomContent, String roomTime) {
        this.roomImg = roomImg;
        this.roomTitle = roomTitle;
        this.roomContent = roomContent;
        this.roomTime = roomTime;
    }

    public int getRoomImg() {
        return roomImg;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public String getRoomContent() {
        return roomContent;
    }

    public String getRoomTime() {
        return roomTime;
    }
}
