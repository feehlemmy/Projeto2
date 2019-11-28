package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pinguimsoftware.gabrielsilva.eduqapp.Utils.FirebaseUtils;

public class LoginPasswordActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText inputSenha;
    private FirebaseAuth mAuth;
    private TextView forgetPassword;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);

        Intent intent = getIntent();

        btnLogin = (Button) findViewById(R.id.btn_login);
        inputSenha = (EditText) findViewById(R.id.password_login_field);
        forgetPassword = findViewById(R.id.lbl_forget_password);

        Bundle bundle = intent.getExtras();
        String email = bundle.getString("email");
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(inputSenha.getText())) {
                    inputSenha.setError("Senha obrigatória.");
                    inputSenha.setFocusable(true);
                }else {
                    String password = inputSenha.getText().toString();
                    login(email, password);
                }
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadResetPasswordActivity();
            }
        });
    }

    private void loadResetPasswordActivity() {
        Intent intentReset = new Intent(this, ResetPasswordActivity.class);
        startActivity(intentReset);
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            loadApplication(email, password);
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getLocalizedMessage().toString();

                FirebaseUtils utils = new FirebaseUtils();
                utils.getError(LoginPasswordActivity.this, error);
            }
        });
    }

    private void loadApplication(String email, String password) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}
