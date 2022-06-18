package com.example.appmovilmc2firebase.utils;

public class GlobalInfo {
    public static final String AUTH_TOKEN = "fYL76zWgqkwZT54YZGKcwtzHE2HHSQ";
    public static final String URL_LOGIN = "http://192.168.1.62/pt__users?login=true&suffix=user";
    public static final String URL_USER = "http://192.168.1.62/pt__users?select=*";
    public static final String URL_PUNTOS_DE_MEDIDA = "http://192.168.1.62/sys__connections?select=*";
    public static final String URL_REGISTER_PUNTO_DE_MEDIDA = "http://192.168.1.62/sys__connections?addpuntomedida=true";
    public static final String URL_CLIENT = "http://192.168.1.62/sys__clients?select=*";
    public static final String URL_CLIENT_SPINNER = "http://192.168.1.62/sys__clients?select=id_client,name&orderBy=id_client&orderMode=ASC";
    public static final String URL_REGISTER_CLIENT = "http://192.168.1.62/sys__clients?addclient=true";
    public static final String URL_DELETE_CLIENT = "http://192.168.1.62/sys__clients?token=no&except=id_client&nameId=id_client&id=";
    public static final String URL_REGISTER = "http://192.168.1.62/pt__users?register=true";
    public static final String URL_PARTNER = "http://192.168.1.62/sys__partners?select=*";
    public static final String URL_SENDPASSWORD = "http://192.168.1.62/pt__users?sendpassword=true&suffix=user";
    public static final String URL_CHANGEPASSWORD = "http://192.168.1.62/pt__users?changepassword=true&suffix=user";
    public static final String URL_INNER_JOIN_PUNTOS_DE_MEDIDA_CLIENTES = "http://192.168.1.62/sys__connections?rel=sys__connections,sys__clients&type=sys__connection,sys__client&select=name";
}
