package com.example.android.bearguestmobile;

import com.google.gson.annotations.SerializedName;

public class Uid {

    @SerializedName("uid")
    private String uid;

    public Uid(String uid) { this.uid = uid; }

    public String getuid() {
        return this.uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }
}
