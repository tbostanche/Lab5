package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class noteActivity extends AppCompatActivity {
    Button saveButton;
    EditText noteContent;
    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        saveButton = findViewById(R.id.saveButton);
        noteContent = findViewById(R.id.noteEditText);
        Intent intent = getIntent();

        noteid = intent.getIntExtra("noteid", -1);

        if (noteid != -1) {
            Note note = welcomeActivity.notes.get(noteid);
            noteContent.setText(note.getContent());
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(view);
            }
        });
    }

    private void save(View view) {
        String content = noteContent.getText().toString();
        SQLiteDatabase database = getApplicationContext().openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper helper = new DBHelper(database);

        String username = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE).getString("username", "");
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            title = "NOTE_" + (welcomeActivity.notes.size() + 1);
            helper.saveNotes(username, title, date, content);
        } else {
            title = "NOTE_" + (noteid + 1);
            helper.updateNote(title, date, content, username);
        }

        Intent intent = new Intent(getApplicationContext(), welcomeActivity.class);
        startActivity(intent);
    }
}