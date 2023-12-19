package com.example.myapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.models.Message;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM message")
    List<Message> index();

    @Query("SELECT * FROM message WHERE id = :id")
    Message get(int id);

    @Query("SELECT * FROM message WHERE chatid = :chatid")
    List<Message> getByChatId(int chatid);

    @Query("SELECT * FROM message WHERE chatid = :chatid")
    LiveData<List<Message>> getByChatIdLiveData(int chatid);

    @Insert
    void insert(Message... messages);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Message> messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);

    @Query("DELETE FROM message")
    void deleteAll();

    @Query("DELETE FROM message WHERE chatid = :chatid")
    void deleteChat(int chatid);

}