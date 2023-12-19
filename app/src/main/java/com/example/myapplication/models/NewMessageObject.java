package com.example.myapplication.models;

public class NewMessageObject {
    public String userName;
    public String content;

    public NewMessageObject(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String UserName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
