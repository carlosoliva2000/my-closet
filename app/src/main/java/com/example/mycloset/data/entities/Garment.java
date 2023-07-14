package com.example.mycloset.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "garment")
public class Garment {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String brand;
    public String category;

    @Override
    public String toString() {
        return "Garment{" +
                "brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
