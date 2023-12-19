package com.example.myapplication.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.models.Chat;

import java.util.List;
@Dao
public interface ChatDao {
    @Query("SELECT * FROM chat")
    List<Chat> index();

    @Query("SELECT * FROM chat WHERE id = :id")
    Chat getById(int id);

    @Query("SELECT * FROM chat WHERE userid = :userid AND contactid = :contactid")
    Chat getByUser(String userid, String contactid);

    @Insert
    void insert(Chat... chats);

    @Update
    void update(Chat... chats);

    @Delete
    void delete(Chat... chats);
}