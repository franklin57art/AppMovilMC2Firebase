package com.example.appmovilmc2firebase.ui.puntosDeMedida;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PuntosmedidaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PuntosmedidaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is puntos de medida fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}