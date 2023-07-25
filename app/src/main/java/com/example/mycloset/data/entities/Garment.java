package com.example.mycloset.data.entities;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "garment")
public class Garment {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String brand;
    public String category;
    public Uri uri;

    @Override
    public String toString() {
        return "Garment{" +
                "brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
