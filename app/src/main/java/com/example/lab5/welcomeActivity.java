package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class welcomeActivity extends AppCompatActivity {
    TextView welcome;
    SharedPreferences sharedPreferences;
    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcome = findViewById(R.id.welcomeTextView);
        Intent intent = getIntent();
        sharedPreferences = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String welcomeMessage = "Welcome " + username + "!";
        welcome.setText(welcomeMessage);


        SQLiteDatabase database = getApplicationContext().openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(database);
        notes = dbHelper.readNotes(username);

        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note: notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.noteList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(getApplicationContext(), noteActivity.class);
                intent1.putExtra("noteid", i);
                startActivity(intent1);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                sharedPreferences.edit().remove("username").apply();
                startActivity(intent);
                break;
            case R.id.addnote:
                Intent intent2 = new Intent(getApplicationContext(), noteActivity.class);
                intent2.putExtra("newNote", true);
                startActivity(intent2);
                break;
        }
        return true;
    }
}