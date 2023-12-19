package com.example.myapplication.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.myapplication.repositories.UsersRepository;
import com.example.myapplication.models.User;

import java.util.List;

public class UserViewModel {
    private LiveData<List<User>> users;
    private UsersRepository repo;

    public UserViewModel(Context context, String server) {
        repo = new UsersRepository(context, server);
    }

    public User get(String username, String password) {
        return repo.get(username, password);
    }

    public User getUser(String username) {
        return repo.get(username);
    }

    public void add(User user) {
        repo.add(user);
    }
}
