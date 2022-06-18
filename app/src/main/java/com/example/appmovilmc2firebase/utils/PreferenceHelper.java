package com.example.appmovilmc2firebase.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private final String INTRO = "intro";
    private final String NAME = "name";
    private final String EMAIL = "email";
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String IDPTUSER = "id_pt_user";
    private final String IDPARTNER = "id_partner";
    private final String TYPE = "TYPE";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }

    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }

    public void putName(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NAME, loginorout);
        edit.commit();
    }

    public String getName() {
        return app_prefs.getString(NAME, "");
    }

    public void putEmail(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(EMAIL, loginorout);
        edit.commit();
    }

    public String getEmail() {
        return app_prefs.getString(EMAIL, "");
    }

    public void putUserName(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(USERNAME, loginorout);
        edit.commit();
    }

    public String getUserName() {
        return app_prefs.getString(USERNAME, "");
    }

    public void putPassword(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(PASSWORD, loginorout);
        edit.commit();
    }

    public String getPassword() {
        return app_prefs.getString(PASSWORD, "");
    }

    public void putIdPtUser(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(IDPTUSER, loginorout);
        edit.commit();
    }

    public String getIdPtUser() {
        return app_prefs.getString(IDPTUSER, "");
    }

    public void putIdPartner(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(IDPARTNER, loginorout);
        edit.commit();
    }

    public String getIdPartner() {
        return app_prefs.getString(IDPARTNER, "");
    }

    public void putType(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(TYPE, loginorout);
        edit.commit();
    }

    public String getType() {
        return app_prefs.getString(TYPE, "");
    }
}
