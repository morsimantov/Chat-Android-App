package com.example.myapplication.users;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @NonNull
    private @PrimaryKey String id;
    private String nickname;
    private String password;
    private String server;
    private String pictureId;

    public User(String id, String nickname, String password, String server, String pictureId) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.server = server;
        this.pictureId = pictureId;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getServer() {
        return server;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

}