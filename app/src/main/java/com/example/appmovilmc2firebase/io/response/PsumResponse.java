package com.example.appmovilmc2firebase.io.response;

import com.example.appmovilmc2firebase.models.Psum;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PsumResponse {

    @SerializedName("allowed_psums")
    List<Psum> suministros;
    String error;

    public PsumResponse(List<Psum> suministros, String erros) {
        this.suministros = suministros;
        this.error = erros;
    }

    public List<Psum> getSuministros() {
        return suministros;
    }

    public void setSuministros(List<Psum> suministros) {
        this.suministros = suministros;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
