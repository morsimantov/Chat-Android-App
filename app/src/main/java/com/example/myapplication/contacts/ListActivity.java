package com.example.myapplication.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.db.AppDB;
import com.example.myapplication.R;
import com.example.myapplication.db.ContactDao;
import com.example.myapplication.messages.MessageActivity;
import com.example.myapplication.models.Contact;
import com.example.myapplication.settings.SettingsActivity;
import com.example.myapplication.viewmodels.ContactViewModel;
import com.example.myapplication.viewmodels.ContactViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private AppDB db;
    private ContactDao contactDao;
    private ArrayList<Contact> contacts;
    private ContactsListAdapter adapter;
    private ListView lvContacts;
    private String username;
    private String profilePic;
    private ContactViewModel contactViewModel;
    private RecyclerView contactsRecyclerView;
    private ContactsListAdapter.ContactsOnClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TextView nickname = (TextView) findViewById(R.id.contacts_nickname);
        ImageView profilePicView = (ImageView) findViewById(R.id.userProfilePic);

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        FloatingActionButton btnSettings = findViewById(R.id.btnSettings);

        ImageView backBtn = findViewById(R.id.contacts_backBtn);

        Intent activityIntent = getIntent();

        setOnClickListener();

        username = activityIntent.getStringExtra("username");
        String nicknameStr = activityIntent.getStringExtra("nickname");
        String profilePicId = activityIntent.getStringExtra("profilePic");

        db = AppDB.getInstance(getApplicationContext());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String server = sharedPreferences.getString("server", "10.0.2.2:7170");
        contactViewModel = ContactViewModelFactory.getViewModelInstance(username, getApplicationContext(), server);

        contactDao = db.contactDao();
        adapter = new ContactsListAdapter(this, listener);

        nickname.setText(nicknameStr);

        contactsRecyclerView = (RecyclerView) findViewById(R.id.contactsRecyclerView);
        contactsRecyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactsRecyclerView.setLayoutManager(linearLayoutManager);

        contactViewModel.getContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.updateContacts(contacts);
                adapter.notifyDataSetChanged();
            }
        });

        if (!profilePicId.equals("")) {
            profilePic = profilePicId;
            byte[] decodedByte = Base64.decode(profilePic, 0);
            Bitmap bitmap = BitmapFactory
                    .decodeByteArray(decodedByte, 0, decodedByte.length);
            profilePicView.setImageBitmap(bitmap);
        }


        btnAdd.setOnClickListener(view -> {
            Intent i = new Intent(this, FormActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        btnSettings.setOnClickListener(view -> {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        });

        contactsRecyclerView.setClickable(true);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            TextView contactNameView = v.findViewById(R.id.contactNameItem);
            String contactName = contactNameView.getText().toString();

            // creating an intent to move to a different activity
            Intent intent = new Intent(getApplicationContext(), MessageActivity.class);

            intent.putExtra("username", username);
            intent.putExtra("profilePicture", profilePic);
            intent.putExtra("contactName", contactName);

            startActivity(intent);
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}