package com.example.myapplication.messages;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MyService;
import com.example.myapplication.chats.Chat;
import com.example.myapplication.R;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AppDB;
import com.example.myapplication.chats.ChatDao;
import com.example.myapplication.contacts.Contact;
import com.example.myapplication.contacts.ContactDao;
import com.example.myapplication.viewmodels.ContactViewModel;
import com.example.myapplication.viewmodels.ContactViewModelFactory;
import com.example.myapplication.viewmodels.MessageViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageActivity extends AppCompatActivity {
    private AppDB db;
    private MessageDao messageDao;
    private ChatDao chatDao;
    private List<Message> messages;
    private RecyclerView messagesRecyclerView;
    private MessagesListAdapter adapter;
    private String username;
    private String contactName;
    private Chat userChat;
    private MessageViewModel messageViewModel;
    private ContactViewModel contactViewModel;
    private int chatId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent activityIntent = getIntent();

        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);


        // firebase - send message from server to any device by getting its token
        // get the id of the app , we will get the token id of the app
        // the app needs to contact the api
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String userToken = task.getResult();
                    }
                });

        username = activityIntent.getStringExtra("username");
        contactName = activityIntent.getStringExtra("contactName");

        db = AppDB.getInstance(getApplicationContext());
        chatDao = db.chatDao();
        messageDao = db.messageDao();
        // get the chat of the user and contact
        userChat = chatDao.getByUser(username, contactName);
        if (userChat != null) {
            chatId = userChat.getId();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String server = sharedPreferences.getString("server", "10.0.2.2:7170");
        messageViewModel = new MessageViewModel(chatId, getApplicationContext(), username, contactName, server);
        contactViewModel = ContactViewModelFactory.getViewModelInstance(username, getApplicationContext(), server);

        adapter = new MessagesListAdapter(this);

        ImageView backBtn = findViewById(R.id.backBtn);
        ImageView btnAddMessage = findViewById(R.id.sendBtn);
        EditText messageEditText = findViewById(R.id.messageEditText);
        TextView contactNameView = findViewById(R.id.chat_contactName);



        contactNameView.setText(contactName);

        messagesRecyclerView = (RecyclerView) findViewById(R.id.chattingRecyclerView);
//        messagesRecyclerView.setHasFixedSize(true);

        messagesRecyclerView.setAdapter(adapter);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshChat);
        swipeRefreshLayout.setEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        messagesRecyclerView.setLayoutManager(linearLayoutManager);

        messageViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                adapter.updateMessages(messages);
                messagesRecyclerView.scrollToPosition(messages.size() - 1);
            }
        });


        btnAddMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getTextMessage = messageEditText.getText().toString();
                // get current timestamp
                final String currentTime = DateFormat.getDateTimeInstance().format(new Date());
                // create a new message
                Message newMessage = new Message(0, getTextMessage, currentTime, true, chatId);
                // add message to database
//                messageDao.insert(newMessage);
                messageViewModel.add(newMessage);
                Contact currentContact = contactViewModel.getContact(contactName);
                currentContact.setLast(getTextMessage);
                currentContact.setLastdate(currentTime);
                contactViewModel.updateContact(currentContact);
                // clear edit text
                messageEditText.setText("");
                swipeRefreshLayout.setRefreshing(false);
//                sendNotification(username, newMessage.content);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




//    private void sendNotification(String title, String message) {
//        String token = "";
//        OkHttpClient client = new OkHttpClient();
//        MediaType mediaType = MediaType.parse("application/json");
//        JSONObject jsonNotificationObj = new JSONObject();
//        JSONObject wholeObj = new JSONObject();
//        try {
//            jsonNotificationObj.put("title", title);
//            jsonNotificationObj.put("body", message);
//            wholeObj.put("to", token);
//            wholeObj.put("notification", jsonNotificationObj);
//        } catch (JSONException e) {
//            Log.d("fail", e.toString());
//        }
//        RequestBody requestBody = RequestBody.create(mediaType, wholeObj.toString());
//        Request request = new Request.Builder().url("https://fcm.googleapis.com/fcm/send")
//                .post(requestBody)
//                .addHeader("Authorization", "key=")
//                .addHeader("Content-Type", "application/json").build();
//        try {
//            Response response = client.newCall(request).execute();
//        } catch (IOException e) {
//            Log.d("fail", e.toString());
//        }
//    }
}