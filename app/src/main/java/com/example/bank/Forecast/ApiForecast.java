package com.example.bank.Forecast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiForecast {
    private static final String ROOT_URL = "http://lunapay1.somee.com/publish/api/";

    public static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getService() {
        return buildRetrofit().create(ApiService.class);
    }
}
