package com.example.mycloset.data.entities;

import android.net.Uri;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity
public class Outfit {
    @PrimaryKey(autoGenerate = true)
    public long outfitId;

    public String name;
    public Uri uri;

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
                "id=" + outfitId +
                ", name='" + name + '\'' +
                ", uri=" + uri +
                '}';
    }
}

