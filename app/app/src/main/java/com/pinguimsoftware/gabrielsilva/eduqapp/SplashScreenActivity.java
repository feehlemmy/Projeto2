package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        showSplashScreen();
    }

    private void showSplashScreen(){
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        String email = preferences.getString("email", null);
        String nome = preferences.getString("nome", null);
        String cpf = preferences.getString("cpf", null);
        Boolean usuario_logado = preferences.getBoolean("usuario_logado", false);

        if (email != null && nome != null && cpf != null && usuario_logado != null){
            Handler handle = new Handler();
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (usuario_logado){
                        Intent intentNow = new Intent(SplashScreenActivity.this, MainActivity.class);
                        intentNow.putExtra("email", email);
                        intentNow.putExtra("name", nome);
                        intentNow.putExtra("cpf", cpf);

                        startActivity(intentNow);
                        finish();
                    }
                }
            }, 1500);
        } else {
            Handler handle = new Handler();
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showLoginActivity();
                }
            }, 1500);
        }
    }

    private void showLoginActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
