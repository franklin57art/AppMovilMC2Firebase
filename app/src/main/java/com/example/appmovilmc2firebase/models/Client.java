package com.example.appmovilmc2firebase.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.type.DateTime;

import java.sql.Date;
import java.sql.Timestamp;

public class Client {

    @SerializedName("id_client")
    @Expose
    private Integer id_client;

    @SerializedName("id_creator")
    @Expose
    private Integer id_creator;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("last_alarm_dt")
    @Expose
    private DateTime last_alarm_dt;

    @SerializedName("type")
    @Expose
    private Integer type;

    @SerializedName("date_start")
    @Expose
    private Timestamp date_start;

    @SerializedName("last_access")
    @Expose
    private DateTime last_access;

    @SerializedName("id_agemex")
    @Expose
    private Integer id_agemex;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("longitud")
    @Expose
    private String longitud;

    @SerializedName("direccion_fiscal")
    @Expose
    private String direccion_fiscal;

    @SerializedName("cod_postal")
    @Expose
    private String cod_postal;

    @SerializedName("id_poblacion")
    @Expose
    private Integer id_poblacion;

    @SerializedName("nombre_cliente")
    @Expose
    private String nombre_cliente;

    @SerializedName("nombre_empresa")
    @Expose
    private String nombre_empresa;

    @SerializedName("id_fiscal")
    @Expose
    private String id_fiscal;

    @SerializedName("telef1")
    @Expose
    private String telef1;

    @SerializedName("telef2")
    @Expose
    private String telef2;

    @SerializedName("cod_agente")
    @Expose
    private String cod_agente;

    @SerializedName("u_programacion")
    @Expose
    private String u_programacion;

    @SerializedName("u_oferta")
    @Expose
    private String u_oferta;

    @SerializedName("u_fisica")
    @Expose
    private String u_fisica;

    @SerializedName("cod_eic_sujeto")
    @Expose
    private String cod_eic_sujeto;

    @SerializedName("cod_eic_up")
    @Expose
    private String cod_eic_up;

    @SerializedName("display_name_sujeto")
    @Expose
    private String display_name_sujeto;

    @SerializedName("display_name_up")
    @Expose
    private String display_name_up;

    @SerializedName("costes_gestion")
    @Expose
    private Double costes_gestion;

    @SerializedName("costes_gestion_mode")
    @Expose
    private Integer costes_gestion_mode;

    @SerializedName("ruta_carpeta")
    @Expose
    private String ruta_carpeta;

    @SerializedName("notes")
    @Expose
    private String notes;

    @SerializedName("exencion_imp_e")
    @Expose
    private Float exencion_imp_e;

    @SerializedName("posible_cliente")
    @Expose
    private Integer posible_cliente;

    @SerializedName("scope")
    @Expose
    private Integer scope;

    @SerializedName("seguim_liqc3")
    @Expose
    private Integer seguim_liqc3;

    @SerializedName("user_meff")
    @Expose
    private String user_meff;

    @SerializedName("pass_meff")
    @Expose
    private String pass_meff;

    @SerializedName("user_ree")
    @Expose
    private String user_ree;

    @SerializedName("pass_ree ")
    @Expose
    private String pass_ree;

    @SerializedName("cod_empresa")
    @Expose
    private String cod_empresa;

    @SerializedName("omie_notif_dests_old")
    @Expose
    private String omie_notif_dests_old;

    @SerializedName("omie_notif_dests_cc_old")
    @Expose
    private String omie_notif_dests_cc_old;

    @SerializedName("id_gestor_doc")
    @Expose
    private Integer id_gestor_doc;

    @SerializedName("hash")
    @Expose
    private String hash;

    @SerializedName("id_tecnico")
    @Expose
    private Integer id_tecnico;

    @SerializedName("panel_type")
    @Expose
    private Integer panel_type;

    @SerializedName("compras_omie")
    @Expose
    private Integer compras_omie;

    @SerializedName("demo_days")
    @Expose
    private Integer demo_days;

    @SerializedName("id_partner")
    @Expose
    private Integer id_partner;

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("scope_mc2")
    @Expose
    private Integer scope_mc2;

    @SerializedName("scope_a21")
    @Expose
    private Integer scope_a21;

    @SerializedName("scope_t21")
    @Expose
    private Integer scope_t21;

    @SerializedName("end_demo")
    @Expose
    private Date end_demo;

    @SerializedName("logo")
    @Expose
    private String logo;

    @SerializedName("date_s_consumidor_directo")
    @Expose
    private Date date_s_consumidor_directo;

    @SerializedName("valid_monit")
    @Expose
    private Integer valid_monit;

    @SerializedName("xml_agemex_invoice")
    @Expose
    private String xml_agemex_invoice;

    public Client(){
        
    }

    public Client(Integer id_client, Integer id_creator, String name, String email, DateTime last_alarm_dt, Integer type, Timestamp date_start, DateTime last_access, Integer id_agemex, String latitud, String longitud, String direccion_fiscal, String cod_postal, Integer id_poblacion, String nombre_cliente, String nombre_empresa, String id_fiscal, String telef1, String telef2, String cod_agente, String u_programacion, String u_oferta, String u_fisica, String cod_eic_sujeto, String cod_eic_up, String display_name_sujeto, String display_name_up, Double costes_gestion, Integer costes_gestion_mode, String ruta_carpeta, String notes, Float exencion_imp_e, Integer posible_cliente, Integer scope, Integer seguim_liqc3, String user_meff, String pass_meff, String user_ree, String pass_ree, String cod_empresa, String omie_notif_dests_old, String omie_notif_dests_cc_old, Integer id_gestor_doc, String hash, Integer id_tecnico, Integer panel_type, Integer compras_omie, Integer demo_days, Integer id_partner, Integer status, Integer scope_mc2, Integer scope_a21, Integer scope_t21, Date end_demo, String logo, Date date_s_consumidor_directo, Integer valid_monit, String xml_agemex_invoice) {
        this.id_client = id_client;
        this.id_creator = id_creator;
        this.name = name;
        this.email = email;
        this.last_alarm_dt = last_alarm_dt;
        this.type = type;
        this.date_start = date_start;
        this.last_access = last_access;
        this.id_agemex = id_agemex;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion_fiscal = direccion_fiscal;
        this.cod_postal = cod_postal;
        this.id_poblacion = id_poblacion;
        this.nombre_cliente = nombre_cliente;
        this.nombre_empresa = nombre_empresa;
        this.id_fiscal = id_fiscal;
        this.telef1 = telef1;
        this.telef2 = telef2;
        this.cod_agente = cod_agente;
        this.u_programacion = u_programacion;
        this.u_oferta = u_oferta;
        this.u_fisica = u_fisica;
        this.cod_eic_sujeto = cod_eic_sujeto;
        this.cod_eic_up = cod_eic_up;
        this.display_name_sujeto = display_name_sujeto;
        this.display_name_up = display_name_up;
        this.costes_gestion = costes_gestion;
        this.costes_gestion_mode = costes_gestion_mode;
        this.ruta_carpeta = ruta_carpeta;
        this.notes = notes;
        this.exencion_imp_e = exencion_imp_e;
        this.posible_cliente = posible_cliente;
        this.scope = scope;
        this.seguim_liqc3 = seguim_liqc3;
        this.user_meff = user_meff;
        this.pass_meff = pass_meff;
        this.user_ree = user_ree;
        this.pass_ree = pass_ree;
        this.cod_empresa = cod_empresa;
        this.omie_notif_dests_old = omie_notif_dests_old;
        this.omie_notif_dests_cc_old = omie_notif_dests_cc_old;
        this.id_gestor_doc = id_gestor_doc;
        this.hash = hash;
        this.id_tecnico = id_tecnico;
        this.panel_type = panel_type;
        this.compras_omie = compras_omie;
        this.demo_days = demo_days;
        this.id_partner = id_partner;
        this.status = status;
        this.scope_mc2 = scope_mc2;
        this.scope_a21 = scope_a21;
        this.scope_t21 = scope_t21;
        this.end_demo = end_demo;
        this.logo = logo;
        this.date_s_consumidor_directo = date_s_consumidor_directo;
        this.valid_monit = valid_monit;
        this.xml_agemex_invoice = xml_agemex_invoice;
    }

    public Integer getId_client() {
        return id_client;
    }

    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    public Integer getId_creator() {
        return id_creator;
    }

    public void setId_creator(Integer id_creator) {
        this.id_creator = id_creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DateTime getLast_alarm_dt() {
        return last_alarm_dt;
    }

    public void setLast_alarm_dt(DateTime last_alarm_dt) {
        this.last_alarm_dt = last_alarm_dt;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getDate_start() {
        return date_start;
    }

    public void setDate_start(Timestamp date_start) {
        this.date_start = date_start;
    }

    public DateTime getLast_access() {
        return last_access;
    }

    public void setLast_access(DateTime last_access) {
        this.last_access = last_access;
    }

    public Integer getId_agemex() {
        return id_agemex;
    }

    public void setId_agemex(Integer id_agemex) {
        this.id_agemex = id_agemex;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDireccion_fiscal() {
        return direccion_fiscal;
    }

    public void setDireccion_fiscal(String direccion_fiscal) {
        this.direccion_fiscal = direccion_fiscal;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }

    public Integer getId_poblacion() {
        return id_poblacion;
    }

    public void setId_poblacion(Integer id_poblacion) {
        this.id_poblacion = id_poblacion;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getId_fiscal() {
        return id_fiscal;
    }

    public void setId_fiscal(String id_fiscal) {
        this.id_fiscal = id_fiscal;
    }

    public String getTelef1() {
        return telef1;
    }

    public void setTelef1(String telef1) {
        this.telef1 = telef1;
    }

    public String getTelef2() {
        return telef2;
    }

    public void setTelef2(String telef2) {
        this.telef2 = telef2;
    }

    public String getCod_agente() {
        return cod_agente;
    }

    public void setCod_agente(String cod_agente) {
        this.cod_agente = cod_agente;
    }

    public String getU_programacion() {
        return u_programacion;
    }

    public void setU_programacion(String u_programacion) {
        this.u_programacion = u_programacion;
    }

    public String getU_oferta() {
        return u_oferta;
    }

    public void setU_oferta(String u_oferta) {
        this.u_oferta = u_oferta;
    }

    public String getU_fisica() {
        return u_fisica;
    }

    public void setU_fisica(String u_fisica) {
        this.u_fisica = u_fisica;
    }

    public String getCod_eic_sujeto() {
        return cod_eic_sujeto;
    }

    public void setCod_eic_sujeto(String cod_eic_sujeto) {
        this.cod_eic_sujeto = cod_eic_sujeto;
    }

    public String getCod_eic_up() {
        return cod_eic_up;
    }

    public void setCod_eic_up(String cod_eic_up) {
        this.cod_eic_up = cod_eic_up;
    }

    public String getDisplay_name_sujeto() {
        return display_name_sujeto;
    }

    public void setDisplay_name_sujeto(String display_name_sujeto) {
        this.display_name_sujeto = display_name_sujeto;
    }

    public String getDisplay_name_up() {
        return display_name_up;
    }

    public void setDisplay_name_up(String display_name_up) {
        this.display_name_up = display_name_up;
    }

    public Double getCostes_gestion() {
        return costes_gestion;
    }

    public void setCostes_gestion(Double costes_gestion) {
        this.costes_gestion = costes_gestion;
    }

    public Integer getCostes_gestion_mode() {
        return costes_gestion_mode;
    }

    public void setCostes_gestion_mode(Integer costes_gestion_mode) {
        this.costes_gestion_mode = costes_gestion_mode;
    }

    public String getRuta_carpeta() {
        return ruta_carpeta;
    }

    public void setRuta_carpeta(String ruta_carpeta) {
        this.ruta_carpeta = ruta_carpeta;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Float getExencion_imp_e() {
        return exencion_imp_e;
    }

    public void setExencion_imp_e(Float exencion_imp_e) {
        this.exencion_imp_e = exencion_imp_e;
    }

    public Integer getPosible_cliente() {
        return posible_cliente;
    }

    public void setPosible_cliente(Integer posible_cliente) {
        this.posible_cliente = posible_cliente;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public Integer getSeguim_liqc3() {
        return seguim_liqc3;
    }

    public void setSeguim_liqc3(Integer seguim_liqc3) {
        this.seguim_liqc3 = seguim_liqc3;
    }

    public String getUser_meff() {
        return user_meff;
    }

    public void setUser_meff(String user_meff) {
        this.user_meff = user_meff;
    }

    public String getPass_meff() {
        return pass_meff;
    }

    public void setPass_meff(String pass_meff) {
        this.pass_meff = pass_meff;
    }

    public String getUser_ree() {
        return user_ree;
    }

    public void setUser_ree(String user_ree) {
        this.user_ree = user_ree;
    }

    public String getPass_ree() {
        return pass_ree;
    }

    public void setPass_ree(String pass_ree) {
        this.pass_ree = pass_ree;
    }

    public String getCod_empresa() {
        return cod_empresa;
    }

    public void setCod_empresa(String cod_empresa) {
        this.cod_empresa = cod_empresa;
    }

    public String getOmie_notif_dests_old() {
        return omie_notif_dests_old;
    }

    public void setOmie_notif_dests_old(String omie_notif_dests_old) {
        this.omie_notif_dests_old = omie_notif_dests_old;
    }

    public String getOmie_notif_dests_cc_old() {
        return omie_notif_dests_cc_old;
    }

    public void setOmie_notif_dests_cc_old(String omie_notif_dests_cc_old) {
        this.omie_notif_dests_cc_old = omie_notif_dests_cc_old;
    }

    public Integer getId_gestor_doc() {
        return id_gestor_doc;
    }

    public void setId_gestor_doc(Integer id_gestor_doc) {
        this.id_gestor_doc = id_gestor_doc;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getId_tecnico() {
        return id_tecnico;
    }

    public void setId_tecnico(Integer id_tecnico) {
        this.id_tecnico = id_tecnico;
    }

    public Integer getPanel_type() {
        return panel_type;
    }

    public void setPanel_type(Integer panel_type) {
        this.panel_type = panel_type;
    }

    public Integer getCompras_omie() {
        return compras_omie;
    }

    public void setCompras_omie(Integer compras_omie) {
        this.compras_omie = compras_omie;
    }

    public Integer getDemo_days() {
        return demo_days;
    }

    public void setDemo_days(Integer demo_days) {
        this.demo_days = demo_days;
    }

    public Integer getId_partner() {
        return id_partner;
    }

    public void setId_partner(Integer id_partner) {
        this.id_partner = id_partner;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getScope_mc2() {
        return scope_mc2;
    }

    public void setScope_mc2(Integer scope_mc2) {
        this.scope_mc2 = scope_mc2;
    }

    public Integer getScope_a21() {
        return scope_a21;
    }

    public void setScope_a21(Integer scope_a21) {
        this.scope_a21 = scope_a21;
    }

    public Integer getScope_t21() {
        return scope_t21;
    }

    public void setScope_t21(Integer scope_t21) {
        this.scope_t21 = scope_t21;
    }

    public Date getEnd_demo() {
        return end_demo;
    }

    public void setEnd_demo(Date end_demo) {
        this.end_demo = end_demo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Date getDate_s_consumidor_directo() {
        return date_s_consumidor_directo;
    }

    public void setDate_s_consumidor_directo(Date date_s_consumidor_directo) {
        this.date_s_consumidor_directo = date_s_consumidor_directo;
    }

    public Integer getValid_monit() {
        return valid_monit;
    }

    public void setValid_monit(Integer valid_monit) {
        this.valid_monit = valid_monit;
    }

    public String getXml_agemex_invoice() {
        return xml_agemex_invoice;
    }

    public void setXml_agemex_invoice(String xml_agemex_invoice) {
        this.xml_agemex_invoice = xml_agemex_invoice;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id_client=" + id_client +
                ", id_creator=" + id_creator +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", last_alarm_dt=" + last_alarm_dt +
                ", type=" + type +
                ", date_start=" + date_start +
                ", last_access=" + last_access +
                ", id_agemex=" + id_agemex +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", direccion_fiscal='" + direccion_fiscal + '\'' +
                ", cod_postal='" + cod_postal + '\'' +
                ", id_poblacion=" + id_poblacion +
                ", nombre_cliente='" + nombre_cliente + '\'' +
                ", nombre_empresa='" + nombre_empresa + '\'' +
                ", id_fiscal='" + id_fiscal + '\'' +
                ", telef1='" + telef1 + '\'' +
                ", telef2='" + telef2 + '\'' +
                ", cod_agente='" + cod_agente + '\'' +
                ", u_programacion='" + u_programacion + '\'' +
                ", u_oferta='" + u_oferta + '\'' +
                ", u_fisica='" + u_fisica + '\'' +
                ", cod_eic_sujeto='" + cod_eic_sujeto + '\'' +
                ", cod_eic_up='" + cod_eic_up + '\'' +
                ", display_name_sujeto='" + display_name_sujeto + '\'' +
                ", display_name_up='" + display_name_up + '\'' +
                ", costes_gestion=" + costes_gestion +
                ", costes_gestion_mode=" + costes_gestion_mode +
                ", ruta_carpeta='" + ruta_carpeta + '\'' +
                ", notes='" + notes + '\'' +
                ", exencion_imp_e=" + exencion_imp_e +
                ", posible_cliente=" + posible_cliente +
                ", scope=" + scope +
                ", seguim_liqc3=" + seguim_liqc3 +
                ", user_meff='" + user_meff + '\'' +
                ", pass_meff='" + pass_meff + '\'' +
                ", user_ree='" + user_ree + '\'' +
                ", pass_ree='" + pass_ree + '\'' +
                ", cod_empresa='" + cod_empresa + '\'' +
                ", omie_notif_dests_old='" + omie_notif_dests_old + '\'' +
                ", omie_notif_dests_cc_old='" + omie_notif_dests_cc_old + '\'' +
                ", id_gestor_doc=" + id_gestor_doc +
                ", hash='" + hash + '\'' +
                ", id_tecnico=" + id_tecnico +
                ", panel_type=" + panel_type +
                ", compras_omie=" + compras_omie +
                ", demo_days=" + demo_days +
                ", id_partner=" + id_partner +
                ", status=" + status +
                ", scope_mc2=" + scope_mc2 +
                ", scope_a21=" + scope_a21 +
                ", scope_t21=" + scope_t21 +
                ", end_demo=" + end_demo +
                ", logo='" + logo + '\'' +
                ", date_s_consumidor_directo=" + date_s_consumidor_directo +
                ", valid_monit=" + valid_monit +
                ", xml_agemex_invoice='" + xml_agemex_invoice + '\'' +
                '}';
    }
}
