package com.example.mycloset.data.entities;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Garment {
    @PrimaryKey(autoGenerate = true)
    public long garmentId;

    public String brand;
    public String category;
    public String name;
    public String colors = "";
    public Uri uri;
    @ColumnInfo(defaultValue = "true")
    public boolean isActive = true;

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    public Date date = new Date();

    public String getFullString(){
        if (name != null)
            return category + " " + brand + " " + name;
        else
            return category + " " + brand + " " + colors.toLowerCase();
    }

    public String getShortString(){
        if (name != null)
            return brand + " " + name;
        else
            return brand + " " + colors.toLowerCase();
    }

    @Override
    public String toString() {
        return "Garment{" +
                "garmentId=" + garmentId +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", colors='" + colors + '\'' +
                ", uri=" + uri +
                ", isActive=" + isActive +
                ", date=" + date +
                '}';
    }
}
