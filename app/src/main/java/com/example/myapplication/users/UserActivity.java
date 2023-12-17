package com.example.myapplication.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class UserActivity extends AppCompatActivity {

    ImageView profilePictureView;
    TextView userNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        profilePictureView = findViewById(R.id.user_image_profile_image);
        userNameView = findViewById(R.id.user_text_user_name);

        Intent activityIntent = getIntent();

        if (activityIntent != null) {
            String userName = activityIntent.getStringExtra("userName");
            int profilePicture = activityIntent.getIntExtra("profilePicture", R.drawable.blue);

            profilePictureView.setImageResource(profilePicture);
            userNameView.setText(userName);
        }
    }
}