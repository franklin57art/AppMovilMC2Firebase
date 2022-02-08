package com.example.appmovilmc2firebase.ui.graficas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GraficasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GraficasViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is Graficas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
