package com.example.myapplication.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.myapplication.models.Invitation;
import com.example.myapplication.models.Contact;
import com.example.myapplication.models.Message;
import com.example.myapplication.models.NewMessageObject;
import com.example.myapplication.models.Transfer;
import com.example.myapplication.models.User;
import com.example.myapplication.models.UserToken;

public interface WebServiceAPI {

    @POST("connectFirebase")
    Call<Void> ConnectFirebase(@Body UserToken userToken);

    @GET("contacts")
    Call<List<Contact>> getContacts(@Query("username") String username);

    @POST("contacts")
    Call<Void> createContact(@Body Contact contact);

    @GET("users")
    Call<User> get(@Query("username") String username, @Query("password") String password);

    @GET("users/getUser")
    Call<User> getUser(@Query("username") String username);

    @POST("users")
    Call<Void> createUser(@Body User user);

    @GET("contacts/{id}/messages")
    Call<List<Message>> getMessages(@Path("id") String id, @Query("username") String username);

    @POST("contacts/{id}/messages")
    Call<Void> createMessage(@Path("id") String id, @Body NewMessageObject message);

    @POST("invitations")
    Call<Void> inviteContact(@Body Invitation invite);

    @POST("transfer")
    Call<Void> transfer(@Body Transfer transfer);

}