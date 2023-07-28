package com.example.mycloset.data.entities;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

@Entity
public class Outfit {
    @PrimaryKey(autoGenerate = true)
    public long outfitId;

    public String name;
    public Uri uri;
    @ColumnInfo(defaultValue = "false")
    public boolean isAutogenerated = false;
    @ColumnInfo(defaultValue = "true")
    public boolean isActive = true;

//    public Outfit() {
//
//    }

//    public Outfit(String name, Uri uri) {
//        super();
//        this.name = name;
//        this.uri = uri;
//    }

    @Override
    public String toString() {
        return "Outfit{" +
                "outfitId=" + outfitId +
                ", name='" + name + '\'' +
                ", uri=" + uri +
                ", isAutogenerated=" + isAutogenerated +
                ", isActive=" + isActive +
                '}';
    }
}

