package com.example.myapplication.api;

import android.util.Log;
import androidx.annotation.NonNull;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.myapplication.models.UserToken;

public class FirebaseAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private String serverUrl;

    public FirebaseAPI(String server) {
        if(server.startsWith("localhost")){
            server = server.replace("localhost","10.0.2.2");
        }
        serverUrl = "http://" +server+ "/api/";
        retrofit = new Retrofit.Builder()
                .baseUrl(this.serverUrl)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void connectFirebase(String username, String token) {
        Call<Void> call = webServiceAPI.ConnectFirebase(new UserToken(username, token));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.i("success", "success get action");

            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.i("fail", "fail");
            }
        });
    }
}
