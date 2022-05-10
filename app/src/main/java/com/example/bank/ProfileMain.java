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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileMain extends AppCompatActivity {
    protected View account_num;
    protected View account_num2;
    protected View account_num3;
    protected View account_num4;
    protected View account_num5;
    protected View account_num6;
    protected TextView profile;
    protected Button button_back;
    protected TextView personal_in;
    protected TextView firstNameMain;
    protected TextView lastNameMain;
    protected  TextView patronymicMain;
    protected TextView birthdayMain;
    protected TextView numberMain;
    protected TextView emailMain;
    protected EditText editFirstName;
    protected EditText editLastName;
    protected EditText editPatronymic;
    protected EditText editBirthday;
    protected EditText editPhone;
    protected EditText editEmail;
    protected String tokenAuth;
    protected JsonElement firstName;
    protected JsonElement lastName;
    protected JsonElement patronymic;
    protected String birthday;
    protected JsonElement number;
    protected JsonElement email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.profile_main);
        account_num=findViewById(R.id.account_num);
        account_num2 = findViewById(R.id.account_num2);
        account_num3 = findViewById(R.id.account_num3);
        account_num4 =findViewById(R.id.account_num4);
        account_num5 = findViewById(R.id.account_num5);
        account_num6 = findViewById(R.id.account_num6);
        profile = findViewById(R.id.profile3);
        button_back = findViewById(R.id.button_back3);
        button_back.setOnClickListener(this::runBack);
        personal_in = findViewById(R.id.personal_in);
        firstNameMain = findViewById(R.id.firstNameMain);
        lastNameMain = findViewById(R.id.LastNameMain);
        patronymicMain = findViewById(R.id.patronymicMain);
        birthdayMain = findViewById(R.id.birthdayMain);
        numberMain = findViewById(R.id.numberPhoneMain);
        emailMain = findViewById(R.id.emailMain);
        editFirstName = findViewById(R.id.editfirstName);
        editLastName = findViewById(R.id.editlastName);
        editPatronymic = findViewById(R.id.editpatronymicM);
        editBirthday = findViewById(R.id.birthdayM);
        editPhone = findViewById(R.id.numberPhoneM);
        editEmail = findViewById(R.id.emailM);
        Intent intent = getIntent();
        tokenAuth = intent.getStringExtra("tokenAuth");
        getInfo();
    }

    private void getInfo(){
        Call<JsonObject> call = ApiForecast.getService().getInfoUser("Bearer "+ tokenAuth);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    firstName = response.body().get("firstName");
                    editFirstName.setText(firstName.getAsString());
                   lastName = response.body().get("lastName");
                   editLastName.setText(lastName.getAsString());
                   patronymic = response.body().get("patronymic");
                   editPatronymic.setText(patronymic.getAsString());
                   email = response.body().get("email");
                   editEmail.setText(email.getAsString());
                   number = response.body().get("phone");
                   editPhone.setText(number.getAsString());
                   birthday =response.body().get("birthday").getAsString();
                   editBirthday.setText(birthday.substring(0,10));
                } else {
                    //действия, если запрос не прощёл
                    Log.d("myLogs", ErrorUtils.errorMessage(response));
                    Toast.makeText(ProfileMain.this, ErrorUtils.errorMessage(response), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ProfileMain.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
