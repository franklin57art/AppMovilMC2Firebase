package com.example.appmovilmc2firebase.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Partner {

    @SerializedName("id_partner")
    @Expose
    private Integer id_partner;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("razon_social")
    @Expose
    private String razon_social;

    @SerializedName("id_fiscal")
    @Expose
    private String id_fiscal;

    @SerializedName("panel_customizations")
    @Expose
    private String panel_customizations;

    @SerializedName("max_users")
    @Expose
    private Integer max_users;

    @SerializedName("max_psums")
    @Expose
    private Integer max_psums;

    @SerializedName("custom_params")
    @Expose
    private String custom_params;

    @SerializedName("custom_domain")
    @Expose
    private String custom_domain;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("multilanguage")
    @Expose
    private Integer multilanguage;

    @SerializedName("mc2")
    @Expose
    private Integer mc2;

    public Integer getId_partner() {
        return id_partner;
    }

    public void setId_partner(Integer id_partner) {
        this.id_partner = id_partner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getId_fiscal() {
        return id_fiscal;
    }

    public void setId_fiscal(String id_fiscal) {
        this.id_fiscal = id_fiscal;
    }

    public String getPanel_customizations() {
        return panel_customizations;
    }

    public void setPanel_customizations(String panel_customizations) {
        this.panel_customizations = panel_customizations;
    }

    public Integer getMax_users() {
        return max_users;
    }

    public void setMax_users(Integer max_users) {
        this.max_users = max_users;
    }

    public Integer getMax_psums() {
        return max_psums;
    }

    public void setMax_psums(Integer max_psums) {
        this.max_psums = max_psums;
    }

    public String getCustom_params() {
        return custom_params;
    }

    public void setCustom_params(String custom_params) {
        this.custom_params = custom_params;
    }

    public String getCustom_domain() {
        return custom_domain;
    }

    public void setCustom_domain(String custom_domain) {
        this.custom_domain = custom_domain;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMultilanguage() {
        return multilanguage;
    }

    public void setMultilanguage(Integer multilanguage) {
        this.multilanguage = multilanguage;
    }

    public Integer getMc2() {
        return mc2;
    }

    public void setMc2(Integer mc2) {
        this.mc2 = mc2;
    }

    public Partner(){

    }

    public Partner(Integer id_partner, String name, String razon_social, String id_fiscal, String panel_customizations, Integer max_users, Integer max_psums, String custom_params, String custom_domain, String title, Integer multilanguage, Integer mc2) {
        this.id_partner = id_partner;
        this.name = name;
        this.razon_social = razon_social;
        this.id_fiscal = id_fiscal;
        this.panel_customizations = panel_customizations;
        this.max_users = max_users;
        this.max_psums = max_psums;
        this.custom_params = custom_params;
        this.custom_domain = custom_domain;
        this.title = title;
        this.multilanguage = multilanguage;
        this.mc2 = mc2;
    }
}
