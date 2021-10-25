package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE);
        username = findViewById(R.id.usernameTextEdit);
        password = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        if (!sharedPreferences.getString("username", "").equals("")) {
            Intent intent = new Intent(getApplicationContext(), welcomeActivity.class);
            intent.putExtra("username", sharedPreferences.getString("username", ""));
            startActivity(intent);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "Username and password required.", Toast.LENGTH_SHORT).show();
                    return;
                }
                sharedPreferences.edit().putString("username", username.getText().toString()).apply();
                Intent intent = new Intent(view.getContext(), welcomeActivity.class);
                intent.putExtra("username", username.getText().toString());
                startActivity(intent);
            }
        });



        }
}