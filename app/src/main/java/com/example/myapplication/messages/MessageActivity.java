package com.example.myapplication.messages;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.MyFirebaseService;
import com.example.myapplication.db.MessageDao;
import com.example.myapplication.models.Chat;
import com.example.myapplication.R;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.db.AppDB;
import com.example.myapplication.db.ChatDao;
import com.example.myapplication.models.Contact;
import com.example.myapplication.models.Message;
import com.example.myapplication.viewmodels.ContactViewModel;
import com.example.myapplication.viewmodels.ContactViewModelFactory;
import com.example.myapplication.viewmodels.MessageViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

        Intent serviceIntent = new Intent(this, MyFirebaseService.class);
        startService(serviceIntent);

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
        messagesRecyclerView.setAdapter(adapter);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshChat);
        swipeRefreshLayout.setEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
                // Get the default time zone
                TimeZone timeZone = TimeZone.getTimeZone("Israel");
                // Create a SimpleDateFormat with the desired date and time format
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                dateFormat.setTimeZone(timeZone);
                // Get the current date and time in the specified time zone
                String currentTime = dateFormat.format(new Date());
                // create a new message
                Message newMessage = new Message(0, getTextMessage, currentTime, true, chatId);
                messageViewModel.add(newMessage);
                Contact currentContact = contactViewModel.getContact(contactName);
                currentContact.setLast(getTextMessage);
                currentContact.setLastdate(currentTime);
                contactViewModel.updateContact(currentContact);
                // clear edit text
                messageEditText.setText("");
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}