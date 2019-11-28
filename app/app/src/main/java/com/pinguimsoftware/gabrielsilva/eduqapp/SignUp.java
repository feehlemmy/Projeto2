package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {
    private Button btnSignUpWithEmail;
    private TextView lblLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignUpWithEmail = findViewById(R.id.btn_signUp_email);
        lblLogin = findViewById(R.id.login_from_signup);

        btnSignUpWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSignUpWithEmail();
            }
        });

        lblLogin.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadLoginForm();
            }
        });
    }

    private void loadSignUpWithEmail() {
        Intent intent = new Intent( SignUp.this, SignUpWithEmailActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadLoginForm(){
        Intent intent = new Intent(SignUp.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
