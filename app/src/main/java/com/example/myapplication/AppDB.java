package com.example.myapplication;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;

import com.example.myapplication.chats.Chat;
import com.example.myapplication.chats.ChatDao;
import com.example.myapplication.contacts.Contact;
import com.example.myapplication.contacts.ContactDao;
import com.example.myapplication.messages.Message;
import com.example.myapplication.messages.MessageDao;
import com.example.myapplication.users.User;
import com.example.myapplication.users.UserDao;

@Database(entities = {Contact.class, User.class, Chat.class, Message.class}, version = 5, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    private static AppDB appDB;

    public static AppDB getInstance(Context context) {
        if (appDB == null) {
            appDB = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "AppDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }
        return appDB;
    }

    public abstract ContactDao contactDao();
    public abstract UserDao userDao();
    public abstract ChatDao chatDao();
    public abstract MessageDao messageDao();
}