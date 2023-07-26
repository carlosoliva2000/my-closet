package com.example.mycloset.data.entities;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Garment {
    @PrimaryKey(autoGenerate = true)
    public long garmentId;

    public String brand;
    public String category;
    public Uri uri;

    @Override
    public String toString() {
        return "Garment{" +
                "id=" + garmentId +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", uri=" + uri +
                '}';
    }
}
