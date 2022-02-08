package com.example.appmovilmc2firebase.ui.usuarios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UsuariosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UsuariosViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is Usuarios fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
