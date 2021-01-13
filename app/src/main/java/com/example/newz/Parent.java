package com.example.newz;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Parent {
    @Nullable
    private String status;

    @Nullable
    private Integer totalResults;

    @Nullable
    private ArrayList<Articles> articles;

    public String getStatus() {
        return status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public ArrayList<Articles> getArticles() {
        return articles;
    }
}
