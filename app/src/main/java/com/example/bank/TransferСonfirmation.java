package com.example.bank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bank.Forecast.ApiForecast;
import com.example.bank.error.ErrorUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferСonfirmation extends AppCompatActivity {
    protected View transfer_cover5;
    protected View transfer_cover6;
    protected View transfer_cover7;
    protected View transfer_cover8;
    protected View transfer_cover9;
    protected TextView moneyTransfer;
    protected TextView cardLastNameTo;
    protected TextView cardPatronymicTo;
    protected TextView cardNameTo2;
    protected TextView cardTo2;
    protected EditText sumTransfer2;
    protected EditText editTextPatronymic;
    protected EditText editTextNumCard;
    protected EditText editName;
    protected EditText editLastName;
    protected String tokenAuth;
    protected String amount;
    protected String numCardTo;
    protected JsonElement firstName;
    protected JsonElement lastName;
    protected JsonElement patronymic;
    protected ImageView ellipse;
    protected ImageView pathel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.transfer_2);
        Intent intent = getIntent();
        tokenAuth = intent.getStringExtra("tokenAuth");
        amount = intent.getStringExtra("summa");
        numCardTo = intent.getStringExtra("cardTo");
        moneyTransfer= findViewById(R.id.moneyTransfer3);
        cardLastNameTo = findViewById(R.id.cardLastNameTo);
        cardPatronymicTo = findViewById(R.id.cardPatronymicTo);
        cardNameTo2 = findViewById(R.id.cardNameTo2);
        cardTo2 = findViewById(R.id.cardTo2);
        transfer_cover5 = findViewById(R.id.transfer_cover5);
        transfer_cover6 = findViewById(R.id.transfer_cover6);
        transfer_cover7 = findViewById(R.id.transfer_cover7);
        transfer_cover8 = findViewById(R.id.transfer_cover8);
        transfer_cover9 = findViewById(R.id.transfer_cover9);
        editTextNumCard = findViewById(R.id.editTextNumberDecimal4);
        editTextNumCard.setText(numCardTo);
        editName = findViewById(R.id.editTextNumberDecimal5);
        editLastName =findViewById(R.id.editTextNumberDecimal6);
        editTextPatronymic= findViewById(R.id.editTextNumberDecimal7);
        sumTransfer2 = findViewById(R.id.editTextNumberDecimal8);
        sumTransfer2.setText(amount);
        ellipse=findViewById(R.id.ellipsTo);
        pathel =findViewById(R.id.ellips);
        getInfo();
    }

    private void getInfo() {
        Call<JsonObject> call = ApiForecast.getService().getInfoTransfer(numCardTo,"Bearer "+ tokenAuth);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                     firstName = response.body().get("firstName");
                    editName.setText(firstName.getAsString());
                    lastName = response.body().get("lastName");
                    editLastName.setText(lastName.getAsString());
                    patronymic = response.body().get("patronymic");
                    editTextPatronymic.setText(patronymic.getAsString());
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        Intent intent = new Intent(TransferСonfirmation.this, MainActivity.class);
                        startActivity(intent);
                    }, 5000);
                } else {
                    //действия, если запрос не прощёл
                    Log.d("myLogs", ErrorUtils.errorMessage(response));
                    Toast.makeText(TransferСonfirmation.this, ErrorUtils.errorMessage(response), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(TransferСonfirmation.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
    }

}
