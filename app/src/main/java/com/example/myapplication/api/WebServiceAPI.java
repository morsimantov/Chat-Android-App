package com.example.myapplication.api;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

//import com.example.mychatapp.Invitation;
//import com.example.mychatapp.Transfer;
import com.example.myapplication.contacts.Contact;
import com.example.myapplication.messages.Message;
import com.example.myapplication.messages.NewMessageObject;
import com.example.myapplication.users.User;
import com.example.myapplication.api.Invitation;

public interface WebServiceAPI {

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