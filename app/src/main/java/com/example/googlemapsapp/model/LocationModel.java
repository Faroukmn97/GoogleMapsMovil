package com.example.googlemapsapp.model;

import com.google.gson.annotations.SerializedName;

public class LocationModel {

    @SerializedName("id_marcador")
    private String id_marcador;

    @SerializedName("facultad")
    private String facultad;

    @SerializedName("decano")
    private String decano;

    @SerializedName("ubicacion")
    private String ubicacion;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("url")
    private String url;


    public String getId_marcador() {
        return id_marcador;
    }

    public void setId_marcador(String id_marcador) {
        this.id_marcador = id_marcador;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getDecano() {
        return decano;
    }

    public void setDecano(String decano) {
        this.decano = decano;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
