package com.example.newz;

import androidx.annotation.Nullable;

public class Articles {
    @Nullable
    Source source;

    @Nullable
    private String author;

    @Nullable
    private String title;

    @Nullable
    private String description;

    @Nullable
    private String url;

    @Nullable
    private String urlToImage;

    @Nullable
    private String publishedAt;

    @Nullable
    private String content;

    public Source getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }
}
