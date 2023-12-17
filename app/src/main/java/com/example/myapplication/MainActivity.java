package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.contacts.ContactsListAdapter;
import com.example.myapplication.contacts.FormActivity;
import com.example.myapplication.contacts.ListActivity;
import com.example.myapplication.users.User;
import com.example.myapplication.users.UserDao;
import com.example.myapplication.viewmodels.ContactViewModel;
import com.example.myapplication.viewmodels.ContactViewModelFactory;
import com.example.myapplication.viewmodels.MessageViewModel;
import com.example.myapplication.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppDB db;
    private UserDao userDao;
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        // firebase - send message from server to any device by getting its token
        // get the id of the app , we will get the token id of the app
        // the app needs to contact the api
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, instanceIdResult -> {
//            String newToken = instanceIdResult.getToken();
//        });

        TextView username = (TextView) findViewById(R.id.login_username);
        TextView password = (TextView) findViewById(R.id.login_password);

        MaterialButton loginBtn = (MaterialButton) findViewById(R.id.loginBtn);
        MaterialButton login_registerBtn = (MaterialButton) findViewById(R.id.login_registerBtn);

        db = AppDB.getInstance(getApplicationContext());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String server = sharedPreferences.getString("server", "10.0.2.2:7170");
        userViewModel = new UserViewModel(getApplicationContext(), server);

        userDao = db.userDao();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().length() < 8) {
                    Toast.makeText(MainActivity.this, "Password should contain at least 8 characters!", Toast.LENGTH_SHORT).show();
                } else {
                    User user = userViewModel.get(username.getText().toString(), password.getText().toString());
                    if (user == null || !(user.getPassword().equals(password.getText().toString()))) {
                        System.out.println("user not found");
                        Toast.makeText(MainActivity.this, "Username or password are not correct.", Toast.LENGTH_SHORT).show();
                    } else {
                        String usernameStr = username.getText().toString();
                        String nicknameStr = user.getNickname();
                        String profilePicId = user.getPictureId();
                        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                        intent.putExtra("username", usernameStr);
                        intent.putExtra("nickname", nicknameStr);
                        intent.putExtra("profilePic", profilePicId);
                        startActivity(intent);
                    }
                }
            }
        });

        login_registerBtn.setOnClickListener(view ->{
            // go to registration page
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}