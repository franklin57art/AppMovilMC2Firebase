package com.example.appmovilmc2firebase.io;

import com.example.appmovilmc2firebase.io.response.PsumResponse;
import com.example.appmovilmc2firebase.models.Psum;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Código creado por Francisco Arestizabal Gil
 * Copyright © 2021 Art57.tech. All rights reserved.
 */
public interface ApiService {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("psums")
    Call <PsumResponse> getPsums();
}
