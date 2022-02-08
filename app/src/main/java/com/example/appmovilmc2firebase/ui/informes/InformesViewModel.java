package com.example.appmovilmc2firebase.ui.informes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InformesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InformesViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is Informes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
