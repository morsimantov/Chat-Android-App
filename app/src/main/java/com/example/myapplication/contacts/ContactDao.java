package com.example.myapplication.contacts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact WHERE username = :username")
    List<Contact> index(String username);
//
//    @Query("SELECT * FROM contact WHERE username = :username")
//    LiveData<List<Contact>> indexLiveData(String username);

//    @Query("SELECT * FROM contact")
//    List<Contact> index();

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