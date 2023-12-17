package com.example.myapplication.settings;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.example.myapplication.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.RegistrationActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    Settings settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // check condition dark mode
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darkTheme);
        } else {
            setTheme(R.style.Theme_Light);
        }

        Button saveBtn = (Button) findViewById(R.id.btnSave);
        TextView serverAddress = (TextView) findViewById(R.id.serverAddress);

        serverAddress.setText("12345");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        SwitchMaterial switchBtn = findViewById(R.id.switchBtn);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // set night mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Toast.makeText(SettingsActivity.this, "Dark mode is on", Toast.LENGTH_SHORT).show();
                } else {
                    // set light mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Toast.makeText(SettingsActivity.this, "Light mode is on", Toast.LENGTH_SHORT).show();
                }
            }
        });

        serverAddress.setOnClickListener(view ->{
            String serverAddressStr = serverAddress.getText().toString();
            settings.setServer(serverAddressStr);
            serverAddress.setText(settings.getServer());
            editor.putString("server", serverAddressStr).apply();
        });

        saveBtn.setOnClickListener(view ->{
            finish();
        });
    }
}