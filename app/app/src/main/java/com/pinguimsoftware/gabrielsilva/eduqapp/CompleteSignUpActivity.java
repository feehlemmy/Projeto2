package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.pinguimsoftware.gabrielsilva.eduqapp.Utils.FirebaseUtils;
import com.pinguimsoftware.gabrielsilva.eduqapp.Utils.MaskEditUtils;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.ResponsavelFirebase;

public class CompleteSignUpActivity extends AppCompatActivity {
	private Boolean success = false;
	private Button next;
	private String cpf;
	private FirebaseAuth mAuth;
	private EditText inputCpf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_sign_up);
		final Bundle bundle = getIntent().getExtras();
		inputCpf = findViewById(R.id.cpf_input);
		inputCpf.addTextChangedListener(MaskEditUtils.mask(inputCpf, MaskEditUtils.FORMAT_CPF));
		mAuth = FirebaseAuth.getInstance();

		next = findViewById(R.id.btn_send);

		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadMainActivity(bundle);
			}
		});
	}

	private void loadMainActivity(Bundle bundle) {
		cpf = inputCpf.getText().toString();
		cpf = MaskEditUtils.unmask(cpf);
		if(TextUtils.isEmpty(inputCpf.getText())) {
			inputCpf.setError("CPF Obrigatorio");
			inputCpf.setFocusable(true);
		}else if(isCPF(cpf) == false) {
			inputCpf.setError("CPF Inválido");
			inputCpf.setFocusable(true);
		}else {
			Intent intentNow = new Intent(CompleteSignUpActivity.this, MainActivity.class);

			intentNow.putExtra("email", bundle.get("email").toString());
			intentNow.putExtra("password", bundle.get("password").toString());
			intentNow.putExtra("name", bundle.get("name").toString());
			intentNow.putExtra("cpf", cpf);

			ResponsavelFirebase responsavel = new ResponsavelFirebase();

            responsavel.setNome(bundle.getString("name"));
            responsavel.setEmail(bundle.getString("email"));
            responsavel.setSenha(bundle.getString("password"));
            responsavel.setCpf(cpf);

            signUpWork(responsavel);

			if(success) {
                Context context = this;
                SharedPreferences sharedPref = context.getSharedPreferences("user_preferences",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("email", bundle.get("email").toString());
                editor.putString("cpf", cpf);
                editor.putString("nome", bundle.get("name").toString());
                editor.putBoolean("usuario_logado", true);

                editor.apply();

				startActivity(intentNow);
				finish();
			}else {
				Toast toast = Toast.makeText(this, "Erro ao cadastrar!", Toast.LENGTH_LONG);
			}
		}
	}

	private void signUpWork(ResponsavelFirebase responsavel) {
		String email = responsavel.getEmail();
		String password = responsavel.getSenha();

		mAuth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							// Sign in success
							FirebaseUser user = mAuth.getCurrentUser();
						}
					}
				}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				String error = e.getLocalizedMessage().toString();

				FirebaseUtils utils = new FirebaseUtils();
				utils.getError(CompleteSignUpActivity.this, error);
			}
		});

		saveResponsavel(responsavel);
	}
	private void saveResponsavel(ResponsavelFirebase responsavel) {
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		db.collection("responsavel").document(responsavel.getCpf()).set(responsavel);
		changeSuccessState();
	}

	private void changeSuccessState() {
		success = true;
	}

	//Verifica se o CPF digitado é um cpf valido
	public static boolean isCPF(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if(CPF.equals("00000000000") ||
				CPF.equals("11111111111") ||
				CPF.equals("22222222222") || CPF.equals("33333333333") ||
				CPF.equals("44444444444") || CPF.equals("55555555555") ||
				CPF.equals("66666666666") || CPF.equals("77777777777") ||
				CPF.equals("88888888888") || CPF.equals("99999999999") ||
				(CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// Calculo do 1o. Digito Verificador
		sm = 0;
		peso = 10;
		for(i = 0; i < 9 ; i++) {
			// converte o i-esimo caractere do CPF em um numero
			// (48 eh a posicao de '0' na tabela ASCII)
			num = (int) (CPF.charAt(i) - 48);
			sm = sm + (num * peso);
			peso = peso - 1;
		}

		r = 11 - (sm % 11);
		if((r == 10) || (r == 11))
			dig10 = '0';
		else dig10 = (char) (r + 48); // converte no respectivo caractere numerico

		// Calculo do 2o. Digito Verificador
		sm = 0;
		peso = 11;
		for(i = 0; i < 10 ; i++) {
			num = (int) (CPF.charAt(i) - 48);
			sm = sm + (num * peso);
			peso = peso - 1;
		}

		r = 11 - (sm % 11);
		if((r == 10) || (r == 11))
			dig11 = '0';
		else dig11 = (char) (r + 48);

		// Verifica se os digitos calculados conferem com os digitos informados.
		if((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
			return (true);
		}else {
			return (false);
		}
	}
}
