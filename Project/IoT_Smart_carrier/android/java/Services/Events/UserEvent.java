package com.example.skcho.smartcarrier.Services.Event;


import com.example.skcho.smartcarrier.Api.UserResponse;

public class UserEvent {
    private UserResponse serverResponse;

    public UserEvent(UserResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

    public UserResponse getUserResponse() {
        return serverResponse;
    }

    public void setUserResponse(UserResponse serverResponse) {
        this.serverResponse = serverResponse;
    }
}
