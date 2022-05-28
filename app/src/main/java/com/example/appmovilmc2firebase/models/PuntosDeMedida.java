package com.example.appmovilmc2firebase.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Psum {

    @SerializedName("id_conn")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("cups")
    @Expose
    private String cups;

    @SerializedName("id_psum_for_client")
    @Expose
    private Integer id_psum_for_client;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCups() {
        return cups;
    }

    public void setCups(String cups) {
        this.cups = cups;
    }

    public Integer getId_psum_for_client() {
        return id_psum_for_client;
    }

    public void setId_psum_for_client(Integer id_psum_for_client) {
        this.id_psum_for_client = id_psum_for_client;
    }

    public Psum(Integer id, String name, String cups, Integer id_psum_for_client) {
        this.id = id;
        this.name = name;
        this.cups = cups;
        this.id_psum_for_client = id_psum_for_client;
    }

    public Psum(){

    }

    @Override
    public String toString() {
        return "Psum{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cups='" + cups + '\'' +
                ", id_psum_for_client=" + id_psum_for_client +
                '}';
    }
}
