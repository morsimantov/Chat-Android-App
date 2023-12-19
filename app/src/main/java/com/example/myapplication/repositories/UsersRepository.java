package com.example.myapplication.repositories;

import android.content.Context;

import com.example.myapplication.db.AppDB;
import com.example.myapplication.api.UsersAPI;
import com.example.myapplication.models.User;
import com.example.myapplication.db.UserDao;

public class UsersRepository {
    private UserDao userDao;
    private UsersAPI userAPI;
    private AppDB db;
    private String server;

    public UsersRepository(Context context, String server) {
        this.db = AppDB.getInstance(context);
        this.userDao = db.userDao();
        this.server = server;
        this.userAPI = new UsersAPI(userDao, server);
    }

    public User get(String username, String password) {
        User user = userDao.get(username);
        if (user != null) {
            return user;
        }
        // Synchronize on an object to wait for userAPI.get to complete
        synchronized (this) {
            userAPI.get(this, username, password);
            try {
                // Wait for the completion of userAPI.get
                this.wait();
            } catch (InterruptedException e) {
                // Handle exceptions if necessary
                e.printStackTrace();
            }
        }
        // Retrieve the user from userDao after userAPI.get has completed
        user = userDao.get(username);
        return user;
    }

    public User get(String username) {
        User user = userDao.get(username);
        if (user != null) {
            return user;
        }
        // Synchronize on an object to wait for userAPI.get to complete
        synchronized (this) {
            userAPI.get(this, username);
            try {
                // Wait for the completion of userAPI.get
                this.wait();
            } catch (InterruptedException e) {
                // Handle exceptions if necessary
                e.printStackTrace();
            }
        }
        // Retrieve the user from userDao after userAPI.get has completed
        user = userDao.get(username);
        return user;
    }

    public void add(User user) {
        userAPI.post(this, user);
    }

    public void handleAPIData(int status, User user) {
        if (status == 200) {
            new Thread(() -> {
                user.setPictureId("");
                userDao.insert(user);
                onUserAPIComplete();
            }).start();
        }
    }

    public void postHandle(int status, User user) {
        if (status == 200) {
            new Thread(() -> {
                userDao.insert(user);
            }).start();
        }
    }

    public synchronized void onUserAPIComplete() {
        // Notify the waiting thread that userAPI.get is complete
        this.notify();
    }
}
