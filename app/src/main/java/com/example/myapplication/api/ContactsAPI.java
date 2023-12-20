package com.example.myapplication.api;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.Invitation;
import com.example.myapplication.models.Contact;
import com.example.myapplication.db.ContactDao;
import com.example.myapplication.contacts.FormActivity;
import com.example.myapplication.repositories.ContactsRepository;

import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactsAPI {
    private final MutableLiveData<List<Contact>> contactListData;
    private final ContactDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    private String serverUrl;

    public ContactsAPI(MutableLiveData<List<Contact>> contactListData, ContactDao dao, String server) {
        this.contactListData = contactListData;
        this.dao = dao;
        if(server.startsWith("localhost")){
            server = server.replace("localhost","10.0.2.2");
        }
        serverUrl = "http://"+server+"/api/";
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(this.serverUrl)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void get(ContactsRepository repository, String username) {
        Call<List<Contact>> call = webServiceAPI.getContacts(username);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(@NonNull Call<List<Contact>> call, @NonNull Response<List<Contact>> response) {
                Log.i("success", "success get action");
                repository.handleAPIData(response.code(), response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Contact>> call, @NonNull Throwable t) {
                Log.e("fail", "Failed to get data", t);
            }
        });
    }

    public void post(ContactsRepository repository, Contact contact) {
        Call<Void> call = webServiceAPI.createContact(contact);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                repository.postHandle(response.code(), contact);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                String responseString = "Error : " + t;
                Toast.makeText(FormActivity.context, responseString, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void inviteContact(ContactsRepository repository, Invitation invite, Contact contact) {
        Call<Void> call = webServiceAPI.inviteContact(invite);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                repository.afterInvite(contact);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                String responseString = "Error : " + t;
                Toast.makeText(FormActivity.context, responseString, Toast.LENGTH_LONG).show();
            }
        });
    }
}