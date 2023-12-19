package com.example.myapplication.models;

public class Invitation {
    private String from;
    private String to;
    private String server;

    public Invitation(String from, String to, String server) {
        this.from = from;
        this.to = to;
        this.server = server;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getServer() {
        return server;
    }
}