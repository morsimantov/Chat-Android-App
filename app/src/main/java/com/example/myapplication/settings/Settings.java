package com.example.myapplication.settings;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Settings {
    @PrimaryKey
    @NonNull
    private String server = "localhost:7170";

    public Settings() {
        this.server = "localhost:7170";
    }

    @NonNull
    public String getServer() {
        return server;
    }

    public void setServer(@NonNull String server) {
        this.server = server;
    }
}