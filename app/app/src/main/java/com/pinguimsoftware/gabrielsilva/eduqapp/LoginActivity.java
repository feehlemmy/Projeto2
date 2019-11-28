package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.pinguimsoftware.gabrielsilva.eduqapp.Utils.FirebaseUtils;

public class LoginActivity extends AppCompatActivity {
	private Button btnEmail;
	private Button btnGoogle;
	private TextView textCreateAccount;
	private GoogleSignInClient googleSignInClient;
	private FirebaseUtils utils = new FirebaseUtils();
	private static final int RC_SIGN_IN = 9001;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		textCreateAccount = (TextView) findViewById(R.id.create_account);
		btnEmail = (Button) findViewById(R.id.btn_email);
		btnGoogle = (Button) findViewById(R.id.btn_google);

		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();

		googleSignInClient = GoogleSignIn.getClient(this, gso);

		mAuth = FirebaseAuth.getInstance();

		textCreateAccount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadSignUpActivity();
			}

		});

		btnEmail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadLoginEmailActivity();
			}
		});

		btnGoogle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loginWithGoogle();
			}
		});
	}

	private void loginWithGoogle() {
		Intent intent = googleSignInClient.getSignInIntent();
		startActivityForResult(intent, RC_SIGN_IN);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == RC_SIGN_IN) {
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			handleSignInResult(task);
		}
	}

	private void handleSignInResult(Task<GoogleSignInAccount> task) {
		try {
			GoogleSignInAccount account = task.getResult(ApiException.class);
			Bundle bundle = new Bundle();
			Intent intent = new Intent(LoginActivity.this, CompleteSignUpActivity.class);

			bundle.putString("name", account.getDisplayName());
			bundle.putString("email", account.getEmail());
			bundle.putString("password", "Google");

			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		} catch (ApiException e) {
			Toast.makeText(getApplicationContext(), "Deu ruim", Toast.LENGTH_SHORT).show();
		}
	}

	private void loadLoginEmailActivity() {
		Intent intent = new Intent(LoginActivity.this, LoginEmailActivity.class);
		startActivity(intent);
		finish();
	}

	private void loadSignUpActivity() {
		Intent intent = new Intent(LoginActivity.this, SignUp.class);
		startActivity(intent);
		finish();
	}
}
