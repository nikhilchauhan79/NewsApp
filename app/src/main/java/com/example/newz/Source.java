package com.example.newz;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public class Source {
    @Nullable
    private String id;

    @Nullable
    private String name;

    @Nullable
    private String description;

    @Nullable
    private String url;


    @Nullable
    private SourceImageParse sourceImageParse;

    @Nullable
    private String country;

    @Nullable
    private String language;

    public Source(@Nullable String id, @Nullable String name, @Nullable String description, @Nullable String url, @Nullable SourceImageParse sourceImageParse, @Nullable String country, @Nullable String language) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.sourceImageParse = sourceImageParse;
        this.country = country;
        this.language = language;
    }

    @Nullable
    public SourceImageParse getSourceImageParse() {
        return sourceImageParse;
    }

    public void setSourceImageParse(@Nullable SourceImageParse sourceImageParse) {
        this.sourceImageParse = sourceImageParse;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    @Nullable
    public String getCountry() {
        return country;
    }

    @Nullable
    public String getLanguage() {
        return language;
    }
}
