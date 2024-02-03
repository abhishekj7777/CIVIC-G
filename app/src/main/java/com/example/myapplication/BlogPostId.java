package com.example.myapplication;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class BlogPostId {

    @Exclude
    public String BlogPostId;

    public <T extends BlogPostId> T withId(@NonNull String id) {
        this.BlogPostId = id;
        return (T) this;
    }
}
