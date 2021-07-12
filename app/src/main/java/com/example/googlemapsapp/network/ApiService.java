package com.example.googlemapsapp.network;

import com.example.googlemapsapp.model.ListLocationModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/webresources/marker")
    Call<ListLocationModel> getAllLocation();

}
