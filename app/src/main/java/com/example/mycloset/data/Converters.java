package com.example.mycloset.data;

import android.net.Uri;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static Uri stringToUri(String value) {
        return value == null ? null : Uri.parse(value);
    }

    @TypeConverter
    public static String uriToString(Uri value) {
        return value == null ? null : value.toString();
    }
}
