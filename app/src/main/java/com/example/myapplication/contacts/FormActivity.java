package com.example.myapplication.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.AppDB;
import com.example.myapplication.R;
import com.example.myapplication.RegistrationActivity;
import com.example.myapplication.chats.Chat;
import com.example.myapplication.chats.ChatDao;
import com.example.myapplication.users.User;
import com.example.myapplication.users.UserDao;
import com.example.myapplication.viewmodels.ContactViewModel;
import com.example.myapplication.viewmodels.ContactViewModelFactory;
import com.example.myapplication.viewmodels.UserViewModel;

public class FormActivity extends AppCompatActivity {

    private AppDB db;
    private ContactDao contactDao;
    private UserDao userDao;
    private ChatDao chatDao;
    private String username;
    public ContactViewModel contactViewModel;
    public UserViewModel userViewModel;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        db = AppDB.getInstance(getApplicationContext());

        Intent activityIntent = getIntent();
        username = activityIntent.getStringExtra("username");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String server = sharedPreferences.getString("server", "10.0.2.2:7170");
        contactViewModel = ContactViewModelFactory.getViewModelInstance(username, getApplicationContext(), server);
        userViewModel = new UserViewModel(getApplicationContext(), server);

        chatDao = db.chatDao();

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view->{
            EditText usernameEdit = findViewById(R.id.username);
            EditText nicknameEdit = findViewById(R.id.nickname);
            String contactName = usernameEdit.getText().toString();
            EditText serverAddress = findViewById(R.id.server);
            Contact currentContact = contactViewModel.getContact(contactName);
            if (currentContact == null) {
                User contactAsUser = userViewModel.getUser(contactName);
                if (contactAsUser == null) {
                    Toast.makeText(FormActivity.this, "Contact doesn't exist", Toast.LENGTH_SHORT).show();
                } else {
                    Contact contact = new Contact(contactName, username, nicknameEdit.getText().toString(), serverAddress.getText().toString(), null,null, contactAsUser.getPictureId());
                    contactViewModel.add(contact);
                    Toast.makeText(FormActivity.context, "Contact added successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            } else {
                Toast.makeText(FormActivity.this, "Contact is already added", Toast.LENGTH_SHORT).show();
            }

        });
    }
}