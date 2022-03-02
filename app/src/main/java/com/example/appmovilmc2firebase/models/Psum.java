package com.example.appmovilmc2firebase.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Psum {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cups")
    @Expose
    private String cups;
    @SerializedName("id_psum_for_client")
    @Expose
    private int idPsumForClient;

    public Psum(int id, String name, String cups, int idPsumForClient) {
        this.id = id;
        this.name = name;
        this.cups = cups;
        this.idPsumForClient = idPsumForClient;
    }

    public Psum() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public static void setName(String name) {
        name = name;
    }

    public String getCups() {
        return cups;
    }

    public static void setCups(String cups) {
        cups = cups;
    }

    public int getIdPsumForClient() {
        return idPsumForClient;
    }

    public void setIdPsumForClient(int idPsumForClient) {
        this.idPsumForClient = idPsumForClient;
    }

    public String getDescription() {
        return "Este es el ID Cups del suministro: " + getCups() + ".";
    }

}
