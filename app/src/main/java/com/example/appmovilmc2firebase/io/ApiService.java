package com.example.appmovilmc2firebase.io;

import com.example.appmovilmc2firebase.models.PsumAllowedPsums;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Código creado por Francisco Arestizabal Gil
 * Copyright © 2021 Art57.tech. All rights reserved.
 */
public interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("psums")
    Call <PsumAllowedPsums> getPsums();
}
