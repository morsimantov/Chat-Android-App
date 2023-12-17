package com.example.myapplication.contacts;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(primaryKeys = {"id", "username"})
public class Contact {
    @NonNull
    private String id;
    @NonNull
    private String username;
    private String name;
    private String server;
    private String last;
    private String lastdate;
    private String profilePic;

    public Contact(String id, String username, String name, String server, String last, String lastdate, String profilePic) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
        this.profilePic = profilePic;
    }


    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getId() {
        return id;
    }

    public void setContactid(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", server='" + server + '\'' +
                ", last='" + last + '\'' +
                ", lastdate='" + lastdate + '\'' +
                '}';
    }
}