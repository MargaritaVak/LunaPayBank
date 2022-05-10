package com.example.bank;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bank.Forecast.ApiForecast;
import com.example.bank.error.ErrorUtils;
import com.example.bank.model.Registration;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Profile extends AppCompatActivity implements View.OnClickListener{
    protected Button button_start;
    protected Button button_back;
    protected TextView profile;
    protected TextView profile2;
    protected TextView firstName;
    protected TextView lastName;
    protected EditText editFirstName;
    protected EditText editLastName;
    protected EditText editPatronymic;
    protected TextView patronymic;
    protected TextView birthday;
    protected EditText editBirthday;
    protected TextView numberPhone;
    protected EditText editNumberPhone;
    protected String password;
    protected String email;
    protected String firstNameText;
    protected String lastNameText;
    protected String patronymicText;
    protected String birthdayText;
    protected String phoneText;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.profile_upd);
        profile=findViewById(R.id.profile);
        profile2=findViewById(R.id.profile2);
        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        editFirstName=findViewById(R.id.editFirstName);
        editLastName=findViewById(R.id.editLastName);
        patronymic=findViewById(R.id.patronymic);
        editPatronymic=findViewById(R.id.editPatronymic);
        birthday=findViewById(R.id.birthday);
        editBirthday=findViewById(R.id.editBirthday);
        numberPhone=findViewById(R.id.numberPhone);
        editNumberPhone=findViewById(R.id.editTextPhone);
        button_back=findViewById(R.id.button_back);
        button_back.setOnClickListener(this::run);
        button_start=findViewById(R.id.button_start4);
        button_start.setOnClickListener(this);
    }
  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  public void onClick(View view) {
      if(editFirstName.getText().toString().trim().equals("")|| editLastName.getText().toString().trim().equals("")||
              editPatronymic.getText().toString().trim().equals("")||editBirthday.getText().toString().trim().equals("")||
              editNumberPhone.getText().toString().trim().equals(""))
          Toast.makeText(Profile.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
      else {
          Intent intent = getIntent();
          password = intent.getStringExtra("passwordEdit");
          email = intent.getStringExtra("emailEdit");
          firstNameText = editFirstName.getText().toString();
          lastNameText = editLastName.getText().toString();
          patronymicText = editPatronymic.getText().toString();
          birthdayText = editBirthday.getText().toString();
          phoneText = editNumberPhone.getText().toString();
          getRegistration();
      }
  }



    private void getRegistration() {
        HashMap<String,String> SendData =new HashMap<>();
        SendData.put("firstName",firstNameText);
        SendData.put("lastName",lastNameText);
        SendData.put("patronymic",patronymicText);
        SendData.put("password",password);
        SendData.put("email",email);
        SendData.put("phone",phoneText);
        SendData.put("birthday",birthdayText);

        Call<Registration> call = ApiForecast.getService().Registration(SendData);
        call.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(@NonNull Call<Registration> call, @NonNull Response<Registration> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Profile.this, "Проверьте почту", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Chan ge Here***
                    startActivity(intent);
                    finish();
                    System.exit(0);

                } else {
                    //действия, если запрос не прощёл
                    Log.d("myLogs", ErrorUtils.errorMessage(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Registration> call, @NonNull Throwable t) {

            }
        });
    }


    public void run(View view) {
        Intent i;
        i = new Intent(this, SignUp.class);
        startActivity(i);
    }

}
