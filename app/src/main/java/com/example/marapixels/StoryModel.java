package com.example.marapixels;
// StoryModel.java
public class StoryModel {
    private String title;
    private String content;
    private int imageResourceId;  // or String imageUrl

    // Default constructor
    public StoryModel(String s, String s1) {
        // Default constructor code (if needed)
    }

    // Constructor with parameters
    public StoryModel(String title, String content, int imageResourceId) {
        this.title = title;
        this.content = content;
        this.imageResourceId = imageResourceId;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}

