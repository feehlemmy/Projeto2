package com.pinguimsoftware.gabrielsilva.eduqapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
	private Button btnSendEmail;
	private EditText inputEmail;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);

		mAuth = FirebaseAuth.getInstance();
		btnSendEmail = (Button) findViewById(R.id.btn_send_email_recover_password);
		inputEmail = (EditText) findViewById(R.id.email_login);

		btnSendEmail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(inputEmail.getText())){
					inputEmail.setError("E-mail obrigatório.");
					inputEmail.setFocusable(true);
				} else {
					sendEmailResetPassword();
				}
			}
		});
	}

	private void sendEmailResetPassword() {
		String email = inputEmail.getText().toString();
		mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
			@Override
			public void onSuccess(Void aVoid) {
				Toast.makeText(ResetPasswordActivity.this,
						"Enviamos um email para redefinição de senha.", Toast.LENGTH_SHORT).show();
				redirectToLogin();
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				Toast.makeText(ResetPasswordActivity.this, "Por favor tente novamente.", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void redirectToLogin() {
		Intent intent = new Intent(ResetPasswordActivity.this, LoginEmailActivity.class);
		startActivity(intent);
		finish();
	}
}
