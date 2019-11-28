package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pinguimsoftware.gabrielsilva.eduqapp.Utils.MaskEditUtils;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.ResponsavelFirebase;

public class ProfileEditActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText inputName;
    private EditText inputTelefone;
    private EditText inputEndereco;
    private EditText inputEnderecoNumero;
    private EditText inputMunicipio;
    private EditText inputCep;
    private Spinner spinnerUf;
    private Button btnSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        inputName = findViewById(R.id.update_name);
        inputTelefone = findViewById(R.id.update_phone);
        inputEndereco = findViewById(R.id.address);
        inputEnderecoNumero = findViewById(R.id.txt_address_number);
        inputCep = findViewById(R.id.update_cep);
        inputMunicipio = findViewById(R.id.update_municipio);
        spinnerUf = findViewById(R.id.spinner_uf);

        inputTelefone.addTextChangedListener(MaskEditUtils.mask(inputTelefone, MaskEditUtils.FORMAT_PHONE));
        inputCep.addTextChangedListener(MaskEditUtils.mask(inputCep, MaskEditUtils.FORMAT_CEP));

        btnSave = findViewById(R.id.btn_save);

        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        inputName.setText(preferences.getString("nome", null));
        String cpf = preferences.getString("cpf", null);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.uf, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerUf.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputName.getText())){
                    inputName.setError("Informe seu nome.");
                    inputName.setFocusable(true);
                } else if (TextUtils.isEmpty(inputTelefone.getText())){
                    inputTelefone.setError("Informe seu telefone.");
                    inputTelefone.setFocusable(true);
                } else if (TextUtils.isEmpty(inputEndereco.getText())){
                    inputEndereco.setError("Informe seu endereço.");
                    inputEndereco.setFocusable(true);
                } else if (TextUtils.isEmpty(inputEnderecoNumero.getText())){
                    inputEnderecoNumero.setError("Informe o número de sua residência.");
                    inputEnderecoNumero.setFocusable(true);
                } else if (TextUtils.isEmpty(inputCep.getText())){
                    inputCep.setError("Informe seu CEP.");
                    inputCep.setFocusable(true);
                } else if (TextUtils.isEmpty(inputMunicipio.getText())){
                    inputMunicipio.setError("Informeu seu município.");
                    inputMunicipio.setFocusable(true);
                } else {
                    updateResponsavel(user, cpf);
                }
            }
        });
    }

    private void updateResponsavel(FirebaseUser user, String cpf) {
        if (user != null){
            DocumentReference docRef = db.collection("responsavel").document(cpf);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    ResponsavelFirebase responsavel = document.toObject(ResponsavelFirebase.class);

                    responsavel.setEmail(user.getEmail());
                    responsavel.setCpf(cpf);
                    responsavel.setEndereco(inputEndereco.getText().toString() + ", "+ inputEnderecoNumero.getText().toString());
                    responsavel.setTelefone(inputTelefone.getText().toString());
                    responsavel.setCep(inputCep.getText().toString());
                    responsavel.setUf(spinnerUf.getSelectedItem().toString());
                    responsavel.setMunicipio(inputMunicipio.getText().toString());

                    updateInFirebaseDatabase(responsavel);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Deu ruim", Toast.LENGTH_SHORT).show();
        }
	}

	private void updateInFirebaseDatabase(ResponsavelFirebase responsavel) {
        DocumentReference responsavelRef = db.collection("responsavel").document(responsavel.getCpf());
        responsavelRef.delete();

        db.collection("responsavel").document(responsavel.getCpf()).set(responsavel);

        Toast.makeText(getApplicationContext(), "Perfil atualizado!", Toast.LENGTH_SHORT).show();
	}

	@Override
    public void onBackPressed() {
        super.finish();
    }
}
