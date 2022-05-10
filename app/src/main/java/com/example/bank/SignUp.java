package com.example.bank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SignUp  extends AppCompatActivity implements View.OnClickListener {
    protected ImageView logotype;
    protected TextView signUp;
    protected TextView email;
    protected EditText editEmail;
    protected TextView password;
    protected EditText editPassword;
    protected TextView passwordRepeat;
    protected EditText editPasswordRepeat;
    protected Button startButton;
    protected TextView register;
    protected TextView registerLogin;
    public @NotNull String  passwordEdit;
    public @NotNull String emailEdit;

    public SignUp() {
        passwordEdit = null;
        emailEdit = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задать fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // задать No Title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.sign_up_empty_state);
        logotype=findViewById(R.id.logotype);
        signUp=findViewById(R.id.some_id);
        email=findViewById(R.id.email);
        editEmail=(EditText)findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.password_1);
        editPassword=findViewById(R.id.editTextTextPassword);
        passwordRepeat=findViewById(R.id.password_repeat);
        editPasswordRepeat=(EditText)findViewById(R.id.editTextTextPassword2);
        startButton=findViewById(R.id.button_start2);
        startButton.setOnClickListener(this);
        register=findViewById(R.id.register);
        registerLogin=findViewById(R.id.register2);
        registerLogin.setOnClickListener(this::run);
    }

    public void run(View view) {
        Intent i;
        i = new Intent(this, LogIn.class);
        startActivity(i);
    }



   @Override
   public void onClick(View view) {
       if (editEmail.getText().toString().trim().equals("") || editPassword.getText().toString().trim().equals("") || editPasswordRepeat.getText().toString().trim().equals(""))
           Toast.makeText(SignUp.this, "Заполните поля", Toast.LENGTH_SHORT).show();
       else {
           if (TextUtils.equals(editPassword.getText(), editPasswordRepeat.getText())) {
              // passwordEdit = editPasswordRepeat.getText().toString();
              // emailEdit = editEmail.getText().toString();
               Intent intent = new Intent(SignUp.this, Profile.class);
               intent.putExtra("emailEdit",editEmail.getText().toString());
               intent.putExtra("passwordEdit",editPasswordRepeat.getText().toString());
               startActivity(intent);  //запускаем активити с данными
           }
           else{
             Toast.makeText(SignUp.this, "Пароль не совпадает", Toast.LENGTH_SHORT).show();
           }
       }
   }

    @NotNull
    public String getEmailEdit() {
        return emailEdit;
    }

    @NotNull
    public String getPasswordEdit() {
        return passwordEdit;
    }

    public void setPasswordEdit(@NotNull String passwordEdit) {
        this.passwordEdit = passwordEdit;
    }

    public void setEmailEdit(@NotNull String emailEdit) {
        this.emailEdit = emailEdit;
    }
}

