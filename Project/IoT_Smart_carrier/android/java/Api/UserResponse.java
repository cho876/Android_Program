package com.example.skcho.smartcarrier.Api;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by skCho on 2018-04-02.
 */

public class UserResponse implements Serializable {
    @SerializedName("returned_username")
    private String username;
    @SerializedName("response_code")
    private int responseCode;

    public UserResponse(String username, String password, int responseCode){
        this.username = username;
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }


    public String getUsername() {
        return username;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
