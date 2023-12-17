package com.example.myapplication.api;

import android.util.Log;



import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import androidx.lifecycle.MutableLiveData;


import com.example.myapplication.contacts.Contact;
import com.example.myapplication.repositories.ContactsRepository;
import com.example.myapplication.repositories.UsersRepository;
import com.example.myapplication.users.User;
import com.example.myapplication.users.UserDao;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersAPI {
    private final UserDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private String serverUrl;

    public UsersAPI(UserDao dao, String server) {
        this.dao = dao;
        if(server.startsWith("localhost")){
            server = server.replace("localhost","10.0.2.2");
        }
        serverUrl = "http://" +server+ "/api/";
        retrofit = new Retrofit.Builder()
                .baseUrl(this.serverUrl) // todo
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get(UsersRepository repository, String username, String password) {
        Call<User> call = webServiceAPI.get(username, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                Log.i("success", "success get action");
                repository.handleAPIData(response.code(), response.body());
            }
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.i("fail", "fail");
            }
        });
    }

    public void get(UsersRepository repository, String username) {
        Call<User> call = webServiceAPI.getUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                Log.i("success", "success get action");
                repository.handleAPIData(response.code(), response.body());
            }
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.i("fail", "fail");
            }
        });
    }

    public void post(UsersRepository repository, User user) {
        Call<Void> call = webServiceAPI.createUser(user);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.i("success", "success post message");
                repository.postHandle(response.code(), user);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.i("fail", "fail post message");
            }
        });
    }
}
