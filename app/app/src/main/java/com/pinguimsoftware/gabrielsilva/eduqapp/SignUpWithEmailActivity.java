package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpWithEmailActivity extends AppCompatActivity {
    private TextView loginFromSignUp;
    private Button next;
    private EditText inputName;
    private EditText inputEmail;
    private EditText inputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_with_email);
        loginFromSignUp =  findViewById(R.id.textView2);
        next =  findViewById(R.id.btn_sign_up);

        loginFromSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLoginActivity();
            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCompleteSignUp();
            }
        });
    }

    private void loadLoginActivity(){
        Intent intent = new Intent(SignUpWithEmailActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void loadCompleteSignUp(){
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        Bundle bundle = new Bundle();

        if(TextUtils.isEmpty(inputName.getText())){
            inputName.setError("Nome obrigatório.");
            inputName.setFocusable(true);
        }else if(TextUtils.isEmpty(inputEmail.getText())){
            inputEmail.setError("E-mail obrigatório.");
            inputEmail.setFocusable(true);
        }else if(TextUtils.isEmpty(inputPassword.getText())){
            inputPassword.setError("Senha obrigatória.");
            inputPassword.setFocusable(true);
        }else {
            String email = inputEmail.getText().toString();
            String name = inputName.getText().toString();
            String password = inputPassword.getText().toString();

            bundle.putString("name", name);
            bundle.putString("email", email);
            bundle.putString("password", password);

            Intent intent = new Intent(SignUpWithEmailActivity.this, CompleteSignUpActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
            finish();
        }
    }
}