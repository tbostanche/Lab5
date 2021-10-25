package com.example.lab5;

public class Note {
    private String username;
    private String date;
    private String title;
    private String content;

    public Note(String username, String date, String title, String content) {
        this.content = content;
        this.date = date;
        this.title = title;
        this.username = username;
    }

    public String getDate() { return date; }
    public String getUsername() { return username; }
    public String getTitle() { return title; }
    public String getContent() { return content; }

}
