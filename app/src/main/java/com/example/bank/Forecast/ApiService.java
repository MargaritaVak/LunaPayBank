package com.example.bank.Forecast;

import com.example.bank.model.LoginApp;
import com.example.bank.model.Registration;
import com.example.bank.model.ShortCode;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("Users/registration")
    Call<Registration> Registration(@Body HashMap<String, String> fields);

    @Headers("Content-Type: application/json")
    @POST("Users/login")
    Call<JsonObject> getToken(@Body HashMap<String,String> body);

    @POST("Users/setshortcode")
    Call<Void> setPinCode(@Query("shortCode") String code, @Header("Authorization")String token);

    @POST("Users/auth")
    Call<JsonObject> getAuth(@Query("shortCode") String code, @Header("Authorization")String token);

    @GET("Accounts/allSum")
    Call<JsonObject>getAllSum(@Header("Authorization")String tokenAuth);

    @GET("Users/info")
    Call<JsonObject>getInfoUser(@Header("Authorization")String tokenAuth);

    @GET("Accounts/info")
    Call<JsonObject>getInfoCard(@Header("Authorization")String tokenAuth);

    @Headers("Content-Type: application/json")
    @POST("Operations/transfer")
    Call<Void> transfer(@Body HashMap<String,String> code, @Header("Authorization")String tokenAuth);

    @GET("Users/info/{numberCard}")
    Call<JsonObject> getInfoTransfer(@Path("numberCard") String number, @Header("Authorization")String tokenAuth);

}
