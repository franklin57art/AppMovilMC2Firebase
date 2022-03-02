package com.example.appmovilmc2firebase.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {

    private static ApiAdapter apiRetrofitClient;
    private static Retrofit retrofit;
    private static String BASE_URL = "https://partners.emececuadrado.com/api/psums/";
    // Creamos un interceptor y le indicamos el log level a usar
    private HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    // Asociamos el interceptor a las peticiones
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public ApiAdapter() {
        Gson gson = new GsonBuilder().setLenient().create();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build()) // <-- usamos el log level
                .build();
    }

    public static synchronized ApiAdapter getInstance() {
        if (apiRetrofitClient == null) {
            apiRetrofitClient = new ApiAdapter();
        }
        return apiRetrofitClient;
    }

    public ApiService getApi() {
        return retrofit.create(ApiService.class);
    }

}
