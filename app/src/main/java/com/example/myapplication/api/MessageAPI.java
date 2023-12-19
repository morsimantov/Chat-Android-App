package com.example.myapplication.api;

//import com.example.myapplication.MyApplication;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

//import com.example.myapplication.Transfer;
import com.example.myapplication.models.Message;
import com.example.myapplication.db.MessageDao;
import com.example.myapplication.models.NewMessageObject;
import com.example.myapplication.models.Transfer;
import com.example.myapplication.repositories.MessagesRepository;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MessageAPI {
    private final MutableLiveData<List<Message>> messageListData;
    private final MessageDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private String serverUrl;


    public MessageAPI(MutableLiveData<List<Message>> messageListData, MessageDao dao, String server) {
        this.messageListData = messageListData;
        this.dao  = dao;
        if(server.startsWith("localhost")){
            server = server.replace("localhost","10.0.2.2");
        }
        serverUrl = "http://"+server+"/api/";
        retrofit = new Retrofit.Builder()
                .baseUrl(this.serverUrl) // todo
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    // get from the api all the messages
    public void get(MessagesRepository repository, String username, String id) {
        Call<List<Message>> call = webServiceAPI.getMessages(id, username);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call,@NonNull Response<List<Message>> response) {
                repository.handleAPIData(response.code(), response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {
                Log.e("fail", "Failed to get data", t);
            }
        });
    }

    public void post(MessagesRepository repository, String id, NewMessageObject message) {
        Call<Void> call = webServiceAPI.createMessage(id, message);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.i("success", "success post message");
                repository.postHandle(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.i("fail", "fail post message");
            }
        });
    }

    public void transfer(MessagesRepository repository, String id, Transfer transfer, NewMessageObject message){
        Call<Void> call=webServiceAPI.transfer(transfer);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("success", "success transfer action");
                repository.afterTransfer(id, message);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("fail", "fail transfer message");

            }
        });
    }
}