package com.example.myapplication.messages;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey
    public int id;
    public String content;
    public String created;
    public Boolean sent;
    public int chatId;

    public Message(int id, String content, String created, Boolean sent, int chatId) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.sent = sent;
        this.chatId = chatId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        chatId = chatId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", sent=" + sent +
                ", ChatId=" + chatId +
                '}';
    }
}