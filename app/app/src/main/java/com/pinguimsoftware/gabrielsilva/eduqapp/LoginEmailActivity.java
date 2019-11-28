package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginEmailActivity extends AppCompatActivity {
    private Button btnNext;
    private EditText inputEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        btnNext = (Button) findViewById(R.id.btn_send_email_recover_password);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmailLogin();
            }
        });
    }

    private void getEmailLogin(){
        inputEmail = (EditText) findViewById(R.id.email_login);

        if(TextUtils.isEmpty(inputEmail.getText())){
            inputEmail.setError("E-mail obrigatório.");
            inputEmail.setFocusable(true);
        } else {
            String email = inputEmail.getText().toString();
            Intent intent = new Intent(this, LoginPasswordActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        }
    }
}
