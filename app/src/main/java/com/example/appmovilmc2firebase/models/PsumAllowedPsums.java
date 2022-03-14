package com.example.appmovilmc2firebase.models;

import com.example.appmovilmc2firebase.models.Psum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PsumAllowedPsums {

    @SerializedName("allowed_psums")
    @Expose
    private ArrayList<Psum> allowed_psums;
    String error;

    public PsumAllowedPsums(ArrayList<Psum> allowed_psums, String error) {
        this.allowed_psums = allowed_psums;
        this.error = error;
    }

    public ArrayList<Psum> getAllowed_psums() {
        return allowed_psums;
    }

    public void setAllowed_psums(ArrayList<Psum> allowed_psums) {
        this.allowed_psums = allowed_psums;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    @Override
    public String toString() {
        return "PsumAllowedPsums{" +
                "allowed_psums=" + allowed_psums +
                ", error='" + error + '\'' +
                '}';
    }
}
