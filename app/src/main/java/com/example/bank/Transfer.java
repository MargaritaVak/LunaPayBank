package com.example.bank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bank.Forecast.ApiForecast;
import com.example.bank.error.ErrorUtils;
import com.example.bank.model.Registration;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transfer  extends AppCompatActivity {
    protected Button button_back;
    protected Button buttonset;
    protected TextView moneyTransfer;
    protected View transfer_cover;
    protected View transfer_cover2;
    protected View transfer_cover3;
    protected EditText editTextFrom;
    protected EditText editTextTo;
    protected EditText editTextSum;
    protected TextView card_to;
    protected TextView card_form;
    protected TextView sumTransfer;
    protected String tokenAuth;
    protected String cardFrom;
    protected String cardTo;
    protected String sum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.transfer);
        button_back = findViewById(R.id.button_back5);
        button_back.setOnClickListener(this::runBack);
        buttonset = findViewById(R.id.button_start6);
        buttonset.setOnClickListener(this::onClick);
        transfer_cover = findViewById(R.id.transfer_cover);
        transfer_cover2 = findViewById(R.id.transfer_cover2);
        transfer_cover3 = findViewById(R.id.transfer_cover3);
        sumTransfer = findViewById(R.id.sumTransfer);
        card_form = findViewById(R.id.cardFrom);
        card_to= findViewById(R.id.cardTo);
        editTextFrom = findViewById(R.id.editTextNumberDecimal);
        editTextTo = findViewById(R.id.editTextNumberDecimal2);
        editTextSum = findViewById(R.id.editTextNumberDecimal3);
        Intent intent = getIntent();
        tokenAuth = intent.getStringExtra("tokenAuth");
    }


    public void onClick(View view) {
        if(editTextSum.getText().toString().trim().equals("")|| editTextFrom.getText().toString().trim().equals("")||
                editTextTo.getText().toString().trim().equals(""))
            Toast.makeText(Transfer.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        else {
            cardFrom = editTextFrom.getText().toString();
            cardTo = editTextTo.getText().toString();
            sum = editTextSum.getText().toString();
            getTransfer();
        }
    }

    private void getTransfer() {
        HashMap<String,String> sendBody = new HashMap<>();
        sendBody.put("numberCardFrom",cardFrom);
        sendBody.put("numberCardTo",cardTo);
        sendBody.put("amount",sum);
        Call<Void> call = ApiForecast.getService().transfer(sendBody,"Bearer "+ tokenAuth);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(Transfer.this, TransferСonfirmation.class);
                    intent.putExtra("cardTo",cardTo);
                    intent.putExtra("tokenAuth",tokenAuth);
                    intent.putExtra("summa",sum);
                    startActivity(intent);

                } else {
                    //действия, если запрос не прощёл
                    Log.d("myLogs", ErrorUtils.errorMessage(response));
                    Toast.makeText(Transfer.this, ErrorUtils.errorMessage(response), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Transfer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }

    public void runBack(View view) {
        Intent i;
        i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}