package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bank.Forecast.ApiForecast;
import com.example.bank.error.ErrorUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    protected TextView lunapay;
    protected ImageView logotype;
    protected View action_bar;
    protected TextView yourCard;
    protected View card;
    protected ImageView visaImage;
    protected TextView visa;
    protected View rectangle;
    protected ImageButton homeButton;
    protected ImageButton cardButton;
    protected ImageButton transferButton;
    protected ImageButton profileButton;
    protected String tokenAuth;
    protected EditText editAmount;
    protected JsonElement amount;
    protected TextView your_allbalanc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        lunapay = findViewById(R.id.lunapay_main);
        logotype = findViewById(R.id.logotypeHome);
        action_bar = findViewById(R.id.action_bar);
        yourCard = findViewById(R.id.yourCard);
        card = findViewById(R.id.card);
        visaImage =findViewById(R.id.visaimageView);
        visa = findViewById(R.id.visa);
        rectangle = findViewById(R.id.wrapper);
        homeButton = findViewById(R.id.homeButton);
        cardButton = findViewById(R.id.cardButton);
        cardButton.setOnClickListener(this::runCardInfo);
        transferButton = findViewById(R.id.transferButton);
        transferButton.setOnClickListener(this::runTransfer);
        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(this::runProfile);
        Intent intent = getIntent();
        tokenAuth = intent.getStringExtra("tokenAuth");
        editAmount = findViewById(R.id.editAmount);
        your_allbalanc = findViewById(R.id.your_allbalanc);
        getAllAmount();


    }


    private void getAllAmount() {
        Call<JsonObject> call = ApiForecast.getService().getAllSum("Bearer "+ tokenAuth);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    amount = response.body().get("value");
                    editAmount.setText(amount.getAsString());
                } else {
                    //действия, если запрос не прощёл
                    Log.d("myLogs", ErrorUtils.errorMessage(response));
                   // Toast.makeText(MainActivity.this, ErrorUtils.errorMessage(response), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
               // Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void runProfile(View view) {
        Intent i;
        i = new Intent(this, ProfileMain.class);
        i.putExtra("tokenAuth",tokenAuth);
        startActivity(i);
    }

    public void runCardInfo(View view) {
        Intent i;
        i = new Intent(this, CardInfo.class);
        i.putExtra("tokenAuth",tokenAuth);
        startActivity(i);
    }
    public void runTransfer(View view) {
        Intent i;
        i = new Intent(this, Transfer.class);
        i.putExtra("tokenAuth",tokenAuth);
        startActivity(i);
    }

}
