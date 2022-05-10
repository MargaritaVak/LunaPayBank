package com.example.bank;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.bank.Forecast.ApiForecast;
import com.example.bank.error.ErrorUtils;
import com.example.bank.model.LoginApp;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.DataOutputStream;
import java.io.IOException;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    protected JsonElement token;
    protected ImageView logotype;
    protected TextView logIn;
    protected TextView email;
    protected EditText editEmail;
    protected TextView password;
    protected EditText editPassword;
    protected Button startButton;
    protected TextView register;
    protected TextView registerSign;
    protected  String emailSign;
    protected  String passwordSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.login_empty);
        logotype=findViewById(R.id.logotype);
        logIn=findViewById(R.id.login);
        email=findViewById(R.id.email2);
        editEmail=findViewById(R.id.editTextTextEmailAddress2);
        password=findViewById(R.id.password_2);
        editPassword=findViewById(R.id.editTextTextPassword3);
        startButton=findViewById(R.id.button_start3);
        startButton.setOnClickListener(this);
        register=findViewById(R.id.registerLog);
        registerSign=findViewById(R.id.registerlog1);
        registerSign.setOnClickListener(this::run);
    }
    public void run(View view) {
        Intent i;
        i = new Intent(this, SignUp.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view)
    {
        if(editEmail.getText().toString().trim().equals("")||editPassword.getText().toString().trim().equals(""))
        {
            Toast.makeText(LogIn.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
        else{
            emailSign = editEmail.getText().toString();
            passwordSign = editPassword.getText().toString();
            getToken();

        }

    }

    private void getToken() {
        HashMap<String,String> sendBody = new HashMap<>();
        sendBody.put("email",emailSign);
        sendBody.put("password",passwordSign);
        Call<JsonObject> call = ApiForecast.getService().getToken(sendBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    token = response.body().get("value");
                    Intent intent = new Intent(LogIn.this, SetPinCode.class);
                    intent.putExtra("token", token.getAsString());
                    startActivity(intent);

                } else {
                    //действия, если запрос не прощёл
                    Log.d("myLogs", ErrorUtils.errorMessage(response));
                    Toast.makeText(LogIn.this, ErrorUtils.errorMessage(response), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LogIn.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }}