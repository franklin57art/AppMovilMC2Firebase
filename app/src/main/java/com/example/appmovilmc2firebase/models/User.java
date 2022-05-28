package com.example.appmovilmc2firebase.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.type.DateTime;

import java.sql.Date;

public class User {

    @SerializedName("id_pt_user")
    @Expose
    private Integer id_pt_user;

    @SerializedName("id_partner")
    @Expose
    private Integer id_partner;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("level")
    @Expose
    private Integer level;

    @SerializedName("type")
    @Expose
    private Integer type;

    @SerializedName("allow_any_useragent")
    @Expose
    private Integer allow_any_useragent;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("provisional_pass")
    @Expose
    private Integer provisional_pass;

    @SerializedName("auth_key")
    @Expose
    private String auth_key;

    @SerializedName("auth_key_expire_date")
    @Expose
    private DateTime auth_key_expire_date;

    @SerializedName("max_active_sessions")
    @Expose
    private Integer max_active_sessions;

    @SerializedName("id_creator")
    @Expose
    private Integer id_creator;

    @SerializedName("id_sys_creator")
    @Expose
    private Integer id_sys_creator;

    @SerializedName("date_creation")
    @Expose
    private DateTime date_creation;

    @SerializedName("birthday")
    @Expose
    private Date birthday;

    @SerializedName("telefono")
    @Expose
    private String telefono;

    @SerializedName("id_fiscal")
    @Expose
    private String id_fiscal;

    @SerializedName("dev_enabled")
    @Expose
    private Integer dev_enabled;

    @SerializedName("master")
    @Expose
    private Integer master;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public java.sql.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId_pt_user() {
        return id_pt_user;
    }

    public void setId_pt_user(Integer id_pt_user) {
        this.id_pt_user = id_pt_user;
    }

    public Integer getId_partner() {
        return id_partner;
    }

    public void setId_partner(Integer id_partner) {
        this.id_partner = id_partner;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getAllow_any_useragent() {
        return allow_any_useragent;
    }

    public void setAllow_any_useragent(Integer allow_any_useragent) {
        this.allow_any_useragent = allow_any_useragent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProvisional_pass() {
        return provisional_pass;
    }

    public void setProvisional_pass(Integer provisional_pass) {
        this.provisional_pass = provisional_pass;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public DateTime getAuth_key_expire_date() {
        return auth_key_expire_date;
    }

    public void setAuth_key_expire_date(DateTime auth_key_expire_date) {
        this.auth_key_expire_date = auth_key_expire_date;
    }

    public Integer getMax_active_sessions() {
        return max_active_sessions;
    }

    public void setMax_active_sessions(Integer max_active_sessions) {
        this.max_active_sessions = max_active_sessions;
    }

    public Integer getId_creator() {
        return id_creator;
    }

    public void setId_creator(Integer id_creator) {
        this.id_creator = id_creator;
    }

    public Integer getId_sys_creator() {
        return id_sys_creator;
    }

    public void setId_sys_creator(Integer id_sys_creator) {
        this.id_sys_creator = id_sys_creator;
    }

    public DateTime getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(DateTime date_creation) {
        this.date_creation = date_creation;
    }

    public String getId_fiscal() {
        return id_fiscal;
    }

    public void setId_fiscal(String id_fiscal) {
        this.id_fiscal = id_fiscal;
    }

    public Integer getDev_enabled() {
        return dev_enabled;
    }

    public void setDev_enabled(Integer dev_enabled) {
        this.dev_enabled = dev_enabled;
    }

    public Integer getMaster() {
        return master;
    }

    public void setMaster(Integer master) {
        this.master = master;
    }

    public User(Integer id_pt_user, Integer id_partner, String username, String password, Integer level, Integer type, Integer allow_any_useragent, String name, String email, Integer status, Integer provisional_pass, String auth_key, DateTime auth_key_expire_date, Integer max_active_sessions, Integer id_creator, Integer id_sys_creator, DateTime date_creation, Date birthday, String telefono, String id_fiscal, Integer dev_enabled, Integer master) {
        this.id_pt_user = id_pt_user;
        this.id_partner = id_partner;
        this.username = username;
        this.password = password;
        this.level = level;
        this.type = type;
        this.allow_any_useragent = allow_any_useragent;
        this.name = name;
        this.email = email;
        this.status = status;
        this.provisional_pass = provisional_pass;
        this.auth_key = auth_key;
        this.auth_key_expire_date = auth_key_expire_date;
        this.max_active_sessions = max_active_sessions;
        this.id_creator = id_creator;
        this.id_sys_creator = id_sys_creator;
        this.date_creation = date_creation;
        this.birthday = birthday;
        this.telefono = telefono;
        this.id_fiscal = id_fiscal;
        this.dev_enabled = dev_enabled;
        this.master = master;
    }

    public User(){

    }

    @Override
    public String toString() {
        return "User{" +
                "id_pt_user=" + id_pt_user +
                ", id_partner=" + id_partner +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                ", type=" + type +
                ", allow_any_useragent=" + allow_any_useragent +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", provisional_pass=" + provisional_pass +
                ", auth_key='" + auth_key + '\'' +
                ", auth_key_expire_date=" + auth_key_expire_date +
                ", max_active_sessions=" + max_active_sessions +
                ", id_creator=" + id_creator +
                ", id_sys_creator=" + id_sys_creator +
                ", date_creation=" + date_creation +
                ", birthday=" + birthday +
                ", telefono=" + telefono +
                ", id_fiscal='" + id_fiscal + '\'' +
                ", dev_enabled=" + dev_enabled +
                ", master=" + master +
                '}';
    }
}
