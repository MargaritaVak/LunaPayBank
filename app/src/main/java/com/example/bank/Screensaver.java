package com.example.bank;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;


public class Screensaver extends AppCompatActivity{
    protected ImageView logotype;
    protected TextView secured_by_;
    protected TextView lunapay1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_start);

        lunapay1=findViewById(R.id.lunapay1);
        logotype=findViewById(R.id.logotype);
        secured_by_=findViewById(R.id.secured_by_);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(Screensaver.this, StartButton.class);
            startActivity(intent);
            finish();
        }, 5000);
    }
}
