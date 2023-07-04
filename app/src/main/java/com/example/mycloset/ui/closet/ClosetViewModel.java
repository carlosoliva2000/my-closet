package com.example.mycloset.ui.closet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClosetViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ClosetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome to your closet! Save your outfits here");
    }

    public LiveData<String> getText() {
        return mText;
    }
}