package com.example.newz;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

public class SourceImageParse {
    @Nullable
    private Bitmap sourceImage;

    @Nullable
    public Bitmap getSourceImage() {
        return sourceImage;
    }

    public SourceImageParse(@Nullable Bitmap sourceImage) {
        this.sourceImage = sourceImage;
    }
}
