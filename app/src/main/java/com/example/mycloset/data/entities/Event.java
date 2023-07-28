package com.example.mycloset.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Event {
    @PrimaryKey(autoGenerate = true)
    public long eventId;

    public String name;
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    public Date date = new Date();
    @ColumnInfo(defaultValue = "false")
    public boolean isAutogenerated = false;
    public long outfitWornId;

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", isAutogenerated=" + isAutogenerated +
                ", outfitWornId=" + outfitWornId +
                '}';
    }
}