package com.example.bank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.bank.Forecast.ApiForecast;
import com.example.bank.error.ErrorUtils;
import com.example.bank.model.LoginApp;
import com.example.bank.model.ShortCode;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinCode extends AppCompatActivity {
    protected Button button_back;
    protected Button button_start;
    protected TextView login_pin;
    protected TextView login_pin2;
    protected modelPinCode pc_1;
    protected String pinCode;
    protected String token;
    protected String tokenAuth;
    protected String setPinCode;
    protected String pinCode2;
    protected String tokenAuth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.pin_code);
        login_pin = findViewById(R.id.login);
        login_pin2 =findViewById(R.id.please_ente);
        button_back = findViewById(R.id.button_back2);
        button_back.setOnClickListener(this::run);
        button_start = findViewById(R.id.button_start5);
        pc_1 = (modelPinCode) findViewById(R.id.fpc_2);
        button_start.setOnClickListener(this::onClick);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        setPinCode = intent.getStringExtra("setCode");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        pinCode2 = preferences.getString("pinCode2", "");
        tokenAuth2 = preferences.getString("tokenAuth", "");

    }

    public void onClick(View view){
        pinCode = pc_1.getPhoneCode();

        if(pinCode.equals(""))
        {
            Toast.makeText(PinCode.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
        else {
            if(pinCode.equals(pinCode2)){
                Intent intent = new Intent(PinCode.this, MainActivity.class);
                intent.putExtra("tokenLogin", token);
                intent.putExtra("tokenAuth",tokenAuth2);
                startActivity(intent);
                finish();
            }

            else{
                if(!pinCode.equals(setPinCode))
            {
                Toast.makeText(PinCode.this, "Введен неверный пароль, повторите", Toast.LENGTH_SHORT).show();
            }
                else getToken();
            }
        }
    }

    private void getToken(){
        Call<JsonObject> call = ApiForecast.getService().getAuth(pinCode, "Bearer "+ token);//
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    tokenAuth = response.body().get("value").getAsString();
                   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PinCode.this);
                   SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("tokenAuth",tokenAuth);
                    editor.putString("pinCode2",pinCode);
                    editor.apply();
                    Intent intent = new Intent(PinCode.this, MainActivity.class);
                    intent.putExtra("tokenLogin", token);
                    intent.putExtra("tokenAuth",tokenAuth);
                    startActivity(intent);

                } else {
                    //действия, если запрос не прощёл
                    Log.d("myLogs", ErrorUtils.errorMessage(response));

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }


    public void run(View view) {
        Intent i;
        i = new Intent(this, LogIn.class);
        startActivity(i);
    }
}