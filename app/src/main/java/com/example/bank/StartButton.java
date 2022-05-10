package com.example.bank;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class StartButton extends AppCompatActivity implements View.OnClickListener {
    protected ImageView logotype;
    protected TextView lunapay1;
    protected TextView lunapay2;
    protected Button button_start;
    protected String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.onboarding_activity_1);
        logotype=findViewById(R.id.logotype);
        lunapay1=findViewById(R.id.lunapay1);
        lunapay2=findViewById(R.id.lunapay2);
        button_start=findViewById(R.id.button_start);
        button_start.setOnClickListener(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
         token = preferences.getString("tokenAuth", "");

    }
    @Override
    public void onClick(View view) {
        Intent i;
        if(token.equals(""))
        {
            i = new Intent(this, SignUp.class);
            startActivity(i);
        }
        else{
        i = new Intent(this, PinCode.class);
        startActivity(i);}
    }


}
