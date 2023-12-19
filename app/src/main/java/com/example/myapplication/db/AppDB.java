package com.example.myapplication.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;

import com.example.myapplication.models.Chat;
import com.example.myapplication.models.Contact;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.User;

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