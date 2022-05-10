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

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPinCode extends AppCompatActivity {
    protected Button button_back;
    protected Button button_start;
    protected TextView set_pin;
    protected TextView set_pin2;
    protected TextView set_pin3;
    protected modelPinCode pc_1;
    protected String pinCode;
    protected String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.set_pin_code);
        button_back=findViewById(R.id.button_back2);
        button_back.setOnClickListener(this::run);
        button_start=findViewById(R.id.button_start4);
        set_pin=findViewById(R.id.set_pin_code);
        set_pin2=findViewById(R.id.please_set_);
        set_pin3=findViewById(R.id.set_pin);
        pc_1 = (modelPinCode) findViewById(R.id.fpc_1);
        // Регистрируем обратный вызов события (может быть записан или не записан в соответствии с реальными потребностями)
        button_start.setOnClickListener(this::onClick);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");


    }

    public void onClick(View view){
        pinCode = pc_1.getPhoneCode();

        if(pinCode.equals(""))
        {
            Toast.makeText(SetPinCode.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
        else {
            getToken();
        }
    }

    private void getToken() {
        Call<Void> call = ApiForecast.getService().setPinCode(pinCode, "Bearer "+ token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(SetPinCode.this, PinCode.class);
                    intent.putExtra("token",token);
                    intent.putExtra("setCode",pinCode);
                    startActivity(intent);
                } else {
                    //действия, если запрос не прощёл
                    Log.d("myLogs", ErrorUtils.errorMessage(response));
                    Toast.makeText(SetPinCode.this, ErrorUtils.errorMessage(response), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(SetPinCode.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }



    public void run(View view) {
        Intent i;
        i = new Intent(this, LogIn.class);
        startActivity(i);
    }



}