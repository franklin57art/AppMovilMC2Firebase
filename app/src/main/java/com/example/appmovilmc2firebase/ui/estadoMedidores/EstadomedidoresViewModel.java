package com.example.appmovilmc2firebase.ui.estadoMedidores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EstadomedidoresViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EstadomedidoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Estado de medidores fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}