package com.example.appmovilmc2firebase.ui.alarmas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlarmaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AlarmaViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is Alarma fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
