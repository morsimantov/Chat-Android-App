package com.example.myapplication.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.models.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact WHERE username = :username")
    List<Contact> index(String username);

    @Query("SELECT * FROM contact WHERE id= :id AND username= :username")
    Contact get(String id, String username);

    @Insert
    void insert(Contact... contacts);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... contacts);

    @Query("DELETE FROM contact WHERE username=:username")
    void deleteAll(String username);

}