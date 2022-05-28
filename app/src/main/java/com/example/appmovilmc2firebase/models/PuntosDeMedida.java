package com.example.appmovilmc2firebase.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.type.DateTime;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class PuntosDeMedida {

    @SerializedName("id_conn")
    @Expose
    private Integer id_conn;

    @SerializedName("id_creator")
    @Expose
    private Integer id_creator;

    @SerializedName("id_client")
    @Expose
    private Integer id_client;

    @SerializedName("id_client2")
    @Expose
    private Integer id_client2;

    @SerializedName("id_psum_for_client")
    @Expose
    private String id_psum_for_client;

    @SerializedName("id_psum_ref")
    @Expose
    private Integer id_psum_ref;

    @SerializedName("cp")
    @Expose
    private String cp;

    @SerializedName("id_country")
    @Expose
    private Integer id_country;

    @SerializedName("idpto")
    @Expose
    private Integer idpto;

    @SerializedName("protocol")
    @Expose
    private Integer protocol;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("ip")
    @Expose
    private String ip;

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("mt_uid")
    @Expose
    private String mt_uid;

    @SerializedName("link")
    @Expose
    private Integer link;

    @SerializedName("device")
    @Expose
    private Integer device;

    @SerializedName("key_")
    @Expose
    private Integer key_;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("cups")
    @Expose
    private String cups;

    @SerializedName("cups_obras")
    @Expose
    private String cups_obras;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("cod_postal")
    @Expose
    private String cod_postal;

    @SerializedName("type_plant")
    @Expose
    private Integer type_plant;

    @SerializedName("id_poblacion")
    @Expose
    private Integer id_poblacion;

    @SerializedName("provincia")
    @Expose
    private String provincia;

    @SerializedName("contact_phone")
    @Expose
    private String contact_phone;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("reactive_alarms")
    @Expose
    private Integer reactive_alarms;

    @SerializedName("last_alarm_dt")
    @Expose
    private Date last_alarm_dt;

    @SerializedName("alquiler_equipo")
    @Expose
    private Float alquiler_equipo;

    @SerializedName("lat")
    @Expose
    private Double lat;

    @SerializedName("area")
    @Expose
    private Float area;

    @SerializedName("lon")
    @Expose
    private Double lon;

    @SerializedName("id_inst")
    @Expose
    private Integer id_inst;

    @SerializedName("type_inst")
    @Expose
    private Integer type_inst;

    @SerializedName("planta")
    @Expose
    private Integer planta;

    @SerializedName("type_monit")
    @Expose
    private Integer type_monit;

    @SerializedName("has_monit")
    @Expose
    private Integer has_monit;

    @SerializedName("_left")
    @Expose
    private BigDecimal _left;

    @SerializedName("_top")
    @Expose
    private BigDecimal _top;

    @SerializedName("font_size_img")
    @Expose
    private Integer font_size_img;

    @SerializedName("font_color_btn")
    @Expose
    private String font_color_btn;

    @SerializedName("bg_color_btn")
    @Expose
    private String bg_color_btn;

    @SerializedName("hidden")
    @Expose
    private Integer hidden;

    @SerializedName("formula")
    @Expose
    private String formula;

    @SerializedName("data_alias")
    @Expose
    private Integer data_alias;

    @SerializedName("data_origin")
    @Expose
    private Integer data_origin;

    @SerializedName("last_download_dt")
    @Expose
    private DateTime last_download_dt;

    @SerializedName("download_freq")
    @Expose
    private Integer download_freq;

    @SerializedName("mags_to_import")
    @Expose
    private String mags_to_import;

    @SerializedName("priority")
    @Expose
    private Integer priority;

    @SerializedName("notes")
    @Expose
    private String notes;

    @SerializedName("scope")
    @Expose
    private Integer scope;

    @SerializedName("id_pt_agemex")
    @Expose
    private Integer id_pt_agemex;

    @SerializedName("mod_alq_eq")
    @Expose
    private Integer mod_alq_eq;

    @SerializedName("modo_atr_potencia")
    @Expose
    private Integer modo_atr_potencia;

    @SerializedName("iec_download")
    @Expose
    private Integer iec_download;

    @SerializedName("instant_download")
    @Expose
    private Integer instant_download;

    @SerializedName("weather_download")
    @Expose
    private Integer weather_download;

    @SerializedName("date_creation")
    @Expose
    private Timestamp date_creation;

    @SerializedName("err_code")
    @Expose
    private Integer err_code;

    @SerializedName("in_use")
    @Expose
    private Integer in_use;

    @SerializedName("id_comerc")
    @Expose
    private Integer id_comerc;

    @SerializedName("scope_mc2")
    @Expose
    private Integer scope_mc2;

    @SerializedName("scope_a21")
    @Expose
    private Integer scope_a21;

    @SerializedName("scope_t21")
    @Expose
    private Integer scope_t21;

    @SerializedName("prices_pot")
    @Expose
    private String prices_pot;

    @SerializedName("exenciones")
    @Expose
    private String exenciones;

    @SerializedName("transf_losses")
    @Expose
    private String transf_losses;

    @SerializedName("recharge_temp")
    @Expose
    private Integer recharge_temp;

    @SerializedName("contrat_duration")
    @Expose
    private Integer contrat_duration;

    @SerializedName("contract_duration_TD")
    @Expose
    private Integer contract_duration_TD;

    @SerializedName("dl_diff_days")
    @Expose
    private Integer dl_diff_days;

    @SerializedName("tar_zone")
    @Expose
    private Integer tar_zone;

    @SerializedName("type")
    @Expose
    private Integer type;

    @SerializedName("type_photovoltaic")
    @Expose
    private Integer type_photovoltaic;

    @SerializedName("id_psum_photovoltaic_ref")
    @Expose
    private Integer id_psum_photovoltaic_ref;

    @SerializedName("id_weather_station")
    @Expose
    private Integer id_weather_station;

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("serial_number")
    @Expose
    private String serial_number;

    @SerializedName("type_photovoltaic_psum")
    @Expose
    private Integer type_photovoltaic_psum;

    @SerializedName("estimated_annual_consumption")
    @Expose
    private Integer estimated_annual_consumption;

    @SerializedName("inicio_compras_omie")
    @Expose
    private Date inicio_compras_omie;

    @SerializedName("datalogger_id")
    @Expose
    private Integer datalogger_id;

    @SerializedName("datalogger_bus")
    @Expose
    private String datalogger_bus;

    @SerializedName("rt_correction_factor")
    @Expose
    private Float rt_correction_factor;

    @SerializedName("type_measuring_equip")
    @Expose
    private String type_measuring_equip;

    @SerializedName("presion")
    @Expose
    private Integer presion;

    public PuntosDeMedida(){

    }

    public PuntosDeMedida(Integer id_conn, Integer id_creator, Integer id_client, Integer id_client2, String id_psum_for_client, Integer id_psum_ref, String cp, Integer id_country, Integer idpto, Integer protocol, String phone, String ip, String uid, String mt_uid, Integer link, Integer device, Integer key_, String name, String description, String status, String cups, String cups_obras, String address, String cod_postal, Integer type_plant, Integer id_poblacion, String provincia, String contact_phone, String email, Integer reactive_alarms, Date last_alarm_dt, Float alquiler_equipo, Double lat, Float area, Double lon, Integer id_inst, Integer type_inst, Integer planta, Integer type_monit, Integer has_monit, BigDecimal _left, BigDecimal _top, Integer font_size_img, String font_color_btn, String bg_color_btn, Integer hidden, String formula, Integer data_alias, Integer data_origin, DateTime last_download_dt, Integer download_freq, String mags_to_import, Integer priority, String notes, Integer scope, Integer id_pt_agemex, Integer mod_alq_eq, Integer modo_atr_potencia, Integer iec_download, Integer instant_download, Integer weather_download, Timestamp date_creation, Integer err_code, Integer in_use, Integer id_comerc, Integer scope_mc2, Integer scope_a21, Integer scope_t21, String prices_pot, String exenciones, String transf_losses, Integer recharge_temp, Integer contrat_duration, Integer contract_duration_TD, Integer dl_diff_days, Integer tar_zone, Integer type, Integer type_photovoltaic, Integer id_psum_photovoltaic_ref, Integer id_weather_station, String model, String serial_number, Integer type_photovoltaic_psum, Integer estimated_annual_consumption, Date inicio_compras_omie, Integer datalogger_id, String datalogger_bus, Float rt_correction_factor, String type_measuring_equip, Integer presion) {
        this.id_conn = id_conn;
        this.id_creator = id_creator;
        this.id_client = id_client;
        this.id_client2 = id_client2;
        this.id_psum_for_client = id_psum_for_client;
        this.id_psum_ref = id_psum_ref;
        this.cp = cp;
        this.id_country = id_country;
        this.idpto = idpto;
        this.protocol = protocol;
        this.phone = phone;
        this.ip = ip;
        this.uid = uid;
        this.mt_uid = mt_uid;
        this.link = link;
        this.device = device;
        this.key_ = key_;
        this.name = name;
        this.description = description;
        this.status = status;
        this.cups = cups;
        this.cups_obras = cups_obras;
        this.address = address;
        this.cod_postal = cod_postal;
        this.type_plant = type_plant;
        this.id_poblacion = id_poblacion;
        this.provincia = provincia;
        this.contact_phone = contact_phone;
        this.email = email;
        this.reactive_alarms = reactive_alarms;
        this.last_alarm_dt = last_alarm_dt;
        this.alquiler_equipo = alquiler_equipo;
        this.lat = lat;
        this.area = area;
        this.lon = lon;
        this.id_inst = id_inst;
        this.type_inst = type_inst;
        this.planta = planta;
        this.type_monit = type_monit;
        this.has_monit = has_monit;
        this._left = _left;
        this._top = _top;
        this.font_size_img = font_size_img;
        this.font_color_btn = font_color_btn;
        this.bg_color_btn = bg_color_btn;
        this.hidden = hidden;
        this.formula = formula;
        this.data_alias = data_alias;
        this.data_origin = data_origin;
        this.last_download_dt = last_download_dt;
        this.download_freq = download_freq;
        this.mags_to_import = mags_to_import;
        this.priority = priority;
        this.notes = notes;
        this.scope = scope;
        this.id_pt_agemex = id_pt_agemex;
        this.mod_alq_eq = mod_alq_eq;
        this.modo_atr_potencia = modo_atr_potencia;
        this.iec_download = iec_download;
        this.instant_download = instant_download;
        this.weather_download = weather_download;
        this.date_creation = date_creation;
        this.err_code = err_code;
        this.in_use = in_use;
        this.id_comerc = id_comerc;
        this.scope_mc2 = scope_mc2;
        this.scope_a21 = scope_a21;
        this.scope_t21 = scope_t21;
        this.prices_pot = prices_pot;
        this.exenciones = exenciones;
        this.transf_losses = transf_losses;
        this.recharge_temp = recharge_temp;
        this.contrat_duration = contrat_duration;
        this.contract_duration_TD = contract_duration_TD;
        this.dl_diff_days = dl_diff_days;
        this.tar_zone = tar_zone;
        this.type = type;
        this.type_photovoltaic = type_photovoltaic;
        this.id_psum_photovoltaic_ref = id_psum_photovoltaic_ref;
        this.id_weather_station = id_weather_station;
        this.model = model;
        this.serial_number = serial_number;
        this.type_photovoltaic_psum = type_photovoltaic_psum;
        this.estimated_annual_consumption = estimated_annual_consumption;
        this.inicio_compras_omie = inicio_compras_omie;
        this.datalogger_id = datalogger_id;
        this.datalogger_bus = datalogger_bus;
        this.rt_correction_factor = rt_correction_factor;
        this.type_measuring_equip = type_measuring_equip;
        this.presion = presion;
    }

    public Integer getId_conn() {
        return id_conn;
    }

    public void setId_conn(Integer id_conn) {
        this.id_conn = id_conn;
    }

    public Integer getId_creator() {
        return id_creator;
    }

    public void setId_creator(Integer id_creator) {
        this.id_creator = id_creator;
    }

    public Integer getId_client() {
        return id_client;
    }

    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    public Integer getId_client2() {
        return id_client2;
    }

    public void setId_client2(Integer id_client2) {
        this.id_client2 = id_client2;
    }

    public String getId_psum_for_client() {
        return id_psum_for_client;
    }

    public void setId_psum_for_client(String id_psum_for_client) {
        this.id_psum_for_client = id_psum_for_client;
    }

    public Integer getId_psum_ref() {
        return id_psum_ref;
    }

    public void setId_psum_ref(Integer id_psum_ref) {
        this.id_psum_ref = id_psum_ref;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Integer getId_country() {
        return id_country;
    }

    public void setId_country(Integer id_country) {
        this.id_country = id_country;
    }

    public Integer getIdpto() {
        return idpto;
    }

    public void setIdpto(Integer idpto) {
        this.idpto = idpto;
    }

    public Integer getProtocol() {
        return protocol;
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMt_uid() {
        return mt_uid;
    }

    public void setMt_uid(String mt_uid) {
        this.mt_uid = mt_uid;
    }

    public Integer getLink() {
        return link;
    }

    public void setLink(Integer link) {
        this.link = link;
    }

    public Integer getDevice() {
        return device;
    }

    public void setDevice(Integer device) {
        this.device = device;
    }

    public Integer getKey_() {
        return key_;
    }

    public void setKey_(Integer key_) {
        this.key_ = key_;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCups() {
        return cups;
    }

    public void setCups(String cups) {
        this.cups = cups;
    }

    public String getCups_obras() {
        return cups_obras;
    }

    public void setCups_obras(String cups_obras) {
        this.cups_obras = cups_obras;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }

    public Integer getType_plant() {
        return type_plant;
    }

    public void setType_plant(Integer type_plant) {
        this.type_plant = type_plant;
    }

    public Integer getId_poblacion() {
        return id_poblacion;
    }

    public void setId_poblacion(Integer id_poblacion) {
        this.id_poblacion = id_poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getReactive_alarms() {
        return reactive_alarms;
    }

    public void setReactive_alarms(Integer reactive_alarms) {
        this.reactive_alarms = reactive_alarms;
    }

    public Date getLast_alarm_dt() {
        return last_alarm_dt;
    }

    public void setLast_alarm_dt(Date last_alarm_dt) {
        this.last_alarm_dt = last_alarm_dt;
    }

    public Float getAlquiler_equipo() {
        return alquiler_equipo;
    }

    public void setAlquiler_equipo(Float alquiler_equipo) {
        this.alquiler_equipo = alquiler_equipo;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Integer getId_inst() {
        return id_inst;
    }

    public void setId_inst(Integer id_inst) {
        this.id_inst = id_inst;
    }

    public Integer getType_inst() {
        return type_inst;
    }

    public void setType_inst(Integer type_inst) {
        this.type_inst = type_inst;
    }

    public Integer getPlanta() {
        return planta;
    }

    public void setPlanta(Integer planta) {
        this.planta = planta;
    }

    public Integer getType_monit() {
        return type_monit;
    }

    public void setType_monit(Integer type_monit) {
        this.type_monit = type_monit;
    }

    public Integer getHas_monit() {
        return has_monit;
    }

    public void setHas_monit(Integer has_monit) {
        this.has_monit = has_monit;
    }

    public BigDecimal get_left() {
        return _left;
    }

    public void set_left(BigDecimal _left) {
        this._left = _left;
    }

    public BigDecimal get_top() {
        return _top;
    }

    public void set_top(BigDecimal _top) {
        this._top = _top;
    }

    public Integer getFont_size_img() {
        return font_size_img;
    }

    public void setFont_size_img(Integer font_size_img) {
        this.font_size_img = font_size_img;
    }

    public String getFont_color_btn() {
        return font_color_btn;
    }

    public void setFont_color_btn(String font_color_btn) {
        this.font_color_btn = font_color_btn;
    }

    public String getBg_color_btn() {
        return bg_color_btn;
    }

    public void setBg_color_btn(String bg_color_btn) {
        this.bg_color_btn = bg_color_btn;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Integer getData_alias() {
        return data_alias;
    }

    public void setData_alias(Integer data_alias) {
        this.data_alias = data_alias;
    }

    public Integer getData_origin() {
        return data_origin;
    }

    public void setData_origin(Integer data_origin) {
        this.data_origin = data_origin;
    }

    public DateTime getLast_download_dt() {
        return last_download_dt;
    }

    public void setLast_download_dt(DateTime last_download_dt) {
        this.last_download_dt = last_download_dt;
    }

    public Integer getDownload_freq() {
        return download_freq;
    }

    public void setDownload_freq(Integer download_freq) {
        this.download_freq = download_freq;
    }

    public String getMags_to_import() {
        return mags_to_import;
    }

    public void setMags_to_import(String mags_to_import) {
        this.mags_to_import = mags_to_import;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public Integer getId_pt_agemex() {
        return id_pt_agemex;
    }

    public void setId_pt_agemex(Integer id_pt_agemex) {
        this.id_pt_agemex = id_pt_agemex;
    }

    public Integer getMod_alq_eq() {
        return mod_alq_eq;
    }

    public void setMod_alq_eq(Integer mod_alq_eq) {
        this.mod_alq_eq = mod_alq_eq;
    }

    public Integer getModo_atr_potencia() {
        return modo_atr_potencia;
    }

    public void setModo_atr_potencia(Integer modo_atr_potencia) {
        this.modo_atr_potencia = modo_atr_potencia;
    }

    public Integer getIec_download() {
        return iec_download;
    }

    public void setIec_download(Integer iec_download) {
        this.iec_download = iec_download;
    }

    public Integer getInstant_download() {
        return instant_download;
    }

    public void setInstant_download(Integer instant_download) {
        this.instant_download = instant_download;
    }

    public Integer getWeather_download() {
        return weather_download;
    }

    public void setWeather_download(Integer weather_download) {
        this.weather_download = weather_download;
    }

    public Timestamp getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Timestamp date_creation) {
        this.date_creation = date_creation;
    }

    public Integer getErr_code() {
        return err_code;
    }

    public void setErr_code(Integer err_code) {
        this.err_code = err_code;
    }

    public Integer getIn_use() {
        return in_use;
    }

    public void setIn_use(Integer in_use) {
        this.in_use = in_use;
    }

    public Integer getId_comerc() {
        return id_comerc;
    }

    public void setId_comerc(Integer id_comerc) {
        this.id_comerc = id_comerc;
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

    public String getPrices_pot() {
        return prices_pot;
    }

    public void setPrices_pot(String prices_pot) {
        this.prices_pot = prices_pot;
    }

    public String getExenciones() {
        return exenciones;
    }

    public void setExenciones(String exenciones) {
        this.exenciones = exenciones;
    }

    public String getTransf_losses() {
        return transf_losses;
    }

    public void setTransf_losses(String transf_losses) {
        this.transf_losses = transf_losses;
    }

    public Integer getRecharge_temp() {
        return recharge_temp;
    }

    public void setRecharge_temp(Integer recharge_temp) {
        this.recharge_temp = recharge_temp;
    }

    public Integer getContrat_duration() {
        return contrat_duration;
    }

    public void setContrat_duration(Integer contrat_duration) {
        this.contrat_duration = contrat_duration;
    }

    public Integer getContract_duration_TD() {
        return contract_duration_TD;
    }

    public void setContract_duration_TD(Integer contract_duration_TD) {
        this.contract_duration_TD = contract_duration_TD;
    }

    public Integer getDl_diff_days() {
        return dl_diff_days;
    }

    public void setDl_diff_days(Integer dl_diff_days) {
        this.dl_diff_days = dl_diff_days;
    }

    public Integer getTar_zone() {
        return tar_zone;
    }

    public void setTar_zone(Integer tar_zone) {
        this.tar_zone = tar_zone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType_photovoltaic() {
        return type_photovoltaic;
    }

    public void setType_photovoltaic(Integer type_photovoltaic) {
        this.type_photovoltaic = type_photovoltaic;
    }

    public Integer getId_psum_photovoltaic_ref() {
        return id_psum_photovoltaic_ref;
    }

    public void setId_psum_photovoltaic_ref(Integer id_psum_photovoltaic_ref) {
        this.id_psum_photovoltaic_ref = id_psum_photovoltaic_ref;
    }

    public Integer getId_weather_station() {
        return id_weather_station;
    }

    public void setId_weather_station(Integer id_weather_station) {
        this.id_weather_station = id_weather_station;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public Integer getType_photovoltaic_psum() {
        return type_photovoltaic_psum;
    }

    public void setType_photovoltaic_psum(Integer type_photovoltaic_psum) {
        this.type_photovoltaic_psum = type_photovoltaic_psum;
    }

    public Integer getEstimated_annual_consumption() {
        return estimated_annual_consumption;
    }

    public void setEstimated_annual_consumption(Integer estimated_annual_consumption) {
        this.estimated_annual_consumption = estimated_annual_consumption;
    }

    public Date getInicio_compras_omie() {
        return inicio_compras_omie;
    }

    public void setInicio_compras_omie(Date inicio_compras_omie) {
        this.inicio_compras_omie = inicio_compras_omie;
    }

    public Integer getDatalogger_id() {
        return datalogger_id;
    }

    public void setDatalogger_id(Integer datalogger_id) {
        this.datalogger_id = datalogger_id;
    }

    public String getDatalogger_bus() {
        return datalogger_bus;
    }

    public void setDatalogger_bus(String datalogger_bus) {
        this.datalogger_bus = datalogger_bus;
    }

    public Float getRt_correction_factor() {
        return rt_correction_factor;
    }

    public void setRt_correction_factor(Float rt_correction_factor) {
        this.rt_correction_factor = rt_correction_factor;
    }

    public String getType_measuring_equip() {
        return type_measuring_equip;
    }

    public void setType_measuring_equip(String type_measuring_equip) {
        this.type_measuring_equip = type_measuring_equip;
    }

    public Integer getPresion() {
        return presion;
    }

    public void setPresion(Integer presion) {
        this.presion = presion;
    }
}
