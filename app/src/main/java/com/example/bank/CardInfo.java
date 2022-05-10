package com.example.bank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bank.Forecast.ApiForecast;
import com.example.bank.error.ErrorUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.StandardProtocolFamily;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardInfo  extends AppCompatActivity {
    protected Button button_back;
    protected TextView cvc;
    protected TextView cvctext;
    protected EditText name_card;
    protected TextView infoCard;
    protected EditText date_card;
    protected TextView your_balance;
    protected View background_card;
    protected EditText num_card;
    protected EditText editSum;
    protected String tokenAuth;
    protected String nameAccount;
    protected String numberCard;
    protected String summa;
    protected String cvcCard;
    protected String dataEnd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.card_main);
        button_back = findViewById(R.id.button_back4);
        button_back.setOnClickListener(this::runBack);
        cvc = findViewById(R.id.cvc);
        cvctext = findViewById(R.id.cvctext);
        name_card = findViewById(R.id.name_card);
        infoCard = findViewById(R.id.infoCard);
        date_card = findViewById(R.id.date_card);
        your_balance = findViewById(R.id.your_balanc);
        background_card = findViewById(R.id.background);
        num_card = findViewById(R.id.num_card);
        editSum = findViewById(R.id.editSum);
        Intent intent = getIntent();
        tokenAuth = intent.getStringExtra("tokenAuth");
        getInfo();
    }

    private void getInfo(){
        Call<JsonObject> call = ApiForecast.getService().getInfoCard("Bearer "+ tokenAuth);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                        nameAccount = response.body().get("nameAccount").getAsString();
                        name_card.setText(nameAccount);
                        numberCard = response.body().get("numberCard").getAsString();
                        num_card.setText(numberCard.substring(0,4)+"-"+numberCard.substring(4,8)+"-"+numberCard.substring(8,12)+"-"+numberCard.substring(12));
                        summa = response.body().get("amount").getAsString();
                        editSum.setText(summa);
                        cvcCard = response.body().get("cvc").getAsString();
                        cvctext.setText(cvcCard);
                        dataEnd = response.body().get("dataEnd").getAsString();
                       date_card.setText(dataEnd.substring(2,4)+"/"+dataEnd.substring(5,7));
                } else {
                    //действия, если запрос не прошёл
                    Log.d("myLogs", ErrorUtils.errorMessage(response));
                    Toast.makeText(CardInfo.this, ErrorUtils.errorMessage(response), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(CardInfo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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