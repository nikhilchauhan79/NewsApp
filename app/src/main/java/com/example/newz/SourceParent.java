package com.example.newz;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SourceParent {

    @Nullable
    private String status;

    @Nullable
    private SourceImageParse sourceImageParse;

    @Nullable
    private ArrayList<Source> sources;

    public SourceParent(@Nullable String status, @Nullable SourceImageParse sourceImageParse, @Nullable ArrayList<Source> sources) {
        this.status = status;
        this.sourceImageParse = sourceImageParse;
        this.sources = sources;
    }

    @Nullable
    public SourceImageParse getSourceImageParse() {
        return sourceImageParse;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    @Nullable
    public ArrayList<Source> getSources() {
        return sources;
    }
}
