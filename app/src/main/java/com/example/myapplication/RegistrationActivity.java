package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.api.FirebaseAPI;
import com.example.myapplication.databinding.ActivityRegistrationBinding;
import com.example.myapplication.db.AppDB;
import com.example.myapplication.models.User;
import com.example.myapplication.db.UserDao;
import com.example.myapplication.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegistrationActivity extends AppCompatActivity {

    private String encodedImage;
    ActivityRegistrationBinding binding;

    private AppDB db;
    private UserDao userDao;
    private UserViewModel userViewModel;
    private FirebaseAPI firebaseAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();

        EditText username = (EditText) findViewById(R.id.register_username);
        EditText nickname = (EditText) findViewById(R.id.Nickname);
        EditText password = (EditText) findViewById(R.id.register_password);
        EditText confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        EditText userServer = (EditText) findViewById(R.id.Server);

        MaterialButton registerBtn = (MaterialButton) findViewById(R.id.registerBtn);
        MaterialButton loginBtn = (MaterialButton) findViewById(R.id.register_loginBtn);

        db = AppDB.getInstance(getApplicationContext());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String server = sharedPreferences.getString("server", "10.0.2.2:7170");
        userViewModel = new UserViewModel(getApplicationContext(), server);
        firebaseAPI = new FirebaseAPI(server);

        userDao = db.userDao();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().length() < 8) {
                    Toast.makeText(RegistrationActivity.this, "Password should contain at least 8 characters!", Toast.LENGTH_SHORT).show();

                }
                else if (username.getText().toString().length() == 0 ||
                        confirmPassword.getText().toString().length() == 0 ||
                        nickname.getText().toString().length() == 0 || userServer.getText().toString().length() == 0) {
                    Toast.makeText(RegistrationActivity.this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                }
                else if (!(password.getText().toString().equals(confirmPassword.getText().toString()))) {
                    Toast.makeText(RegistrationActivity.this, "The password confirmation does not match", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().matches("[0-9]+") || password.getText().toString().matches("[A-Za-z]+")) {
                    Toast.makeText(RegistrationActivity.this, "Password must contain letters and numbers", Toast.LENGTH_SHORT).show();
                }
//                else if (!isValidRegistrationDetails()) {
//                    Toast.makeText(RegistrationActivity.this, "Please add a profile picture", Toast.LENGTH_SHORT).show();
//                }
                else {
                    String usernameStr = username.getText().toString();
//                    User user = userDao.get(username.getText().toString());
                    User user = userViewModel.get(username.getText().toString(), password.getText().toString());
                    if (user == null) {
                        if (encodedImage == null) {
                            encodedImage = "";
                        }
                        User newUser = new User(username.getText().toString(), nickname.getText().toString(), password.getText().toString(), userServer.getText().toString(), encodedImage);
//                        userDao.insert(newUser);
                        userViewModel.add(newUser);
                        Toast.makeText(RegistrationActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        // send the server his token.
                        FirebaseMessaging.getInstance().getToken()
                                .addOnCompleteListener(new OnCompleteListener<String>() {
                                    @Override
                                    public void onComplete(@NonNull Task<String> task) {
                                        if (!task.isSuccessful()) {
                                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                                            return;
                                        }
                                        // Get new FCM registration token
                                        String token = task.getResult();
                                        System.out.println("token: " + token);
                                        firebaseAPI.connectFirebase(usernameStr, token);
                                    }
                                });
                        Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(RegistrationActivity.this, "User is already registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to login page
                Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void setListeners() {
        binding.layoutImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }

    private String encodedImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            binding.profileImage.setImageBitmap(bitmap);
//                            binding.textAddImage.setVisibility(View.GONE);
                            encodedImage = encodedImage(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private Boolean isValidRegistrationDetails() {
        if (encodedImage == null) {
            Toast.makeText(RegistrationActivity.this, "Select profile image", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}