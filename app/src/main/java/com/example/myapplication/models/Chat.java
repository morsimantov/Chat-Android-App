package com.example.myapplication.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Chat {
    @PrimaryKey
    public int id;
    public String userid;
    public String contactid;

    public Chat(int id, String userid, String contactid) {
        this.id = id;
        this.userid = userid;
        this.contactid = contactid;
    }

    public int getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getContactid() {
        return contactid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }
}