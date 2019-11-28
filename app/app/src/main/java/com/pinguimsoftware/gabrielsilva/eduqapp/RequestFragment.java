package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pinguimsoftware.gabrielsilva.eduqapp.Utils.MaskEditUtils;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.AlunoFirebase;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.Requisicao;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.ResponsavelFirebase;

import java.util.Calendar;
import java.util.List;

public class RequestFragment extends Fragment {
    private EditText date;
    private EditText request;
    private Button sendRequest;
    private Spinner studentsSpinner;
	private Spinner requestTypeSpinner;
	private List<AlunoFirebase> students = null;
	private FirebaseFirestore db = FirebaseFirestore.getInstance();
	DatePickerDialog.OnDateSetListener setListener;

    @Nullable
    @Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_request, null);
		Bundle data = getArguments();
		date = view.findViewById(R.id.request_date);
        request = view.findViewById(R.id.request_input);
        studentsSpinner = view.findViewById(R.id.request_spinner);
        sendRequest = view.findViewById(R.id.send_request);
		requestTypeSpinner = view.findViewById(R.id.spinner_tipo_requisicao);

		date.addTextChangedListener(MaskEditUtils.mask(date, MaskEditUtils.FORMAT_DATE));

		Calendar calendar = Calendar.getInstance();
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DAY_OF_MONTH);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.request_types, R.layout.support_simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
		requestTypeSpinner.setAdapter(adapter);

		date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog datePickerDialog = new DatePickerDialog(
						getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
				datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				datePickerDialog.show();
			}
		});

		setListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
				month ++;
				String dataCalendario = day + "/" + month + "/" + year;
				date.setText(dataCalendario);
			}
		};

		setStudents(data);

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	String testData = "Data de requerimento";
				if(TextUtils.isEmpty(request.getText())){
					request.setError("Descreva sua requisição.");
					request.setFocusable(true);
				} else if(TextUtils.isEmpty(date.getText())) {
					date.setError("Informe a data.");
				} else {
					contructRequestFirebase(data);
				}
            }
        });

        return view;
    }

	private void contructRequestFirebase(Bundle data) {
		String studentSelected = studentsSpinner.getSelectedItem().toString();
		String typeSelected = requestTypeSpinner.getSelectedItem().toString();

		loadResponsavelData(data.getString("responsavelCpf"), studentSelected, typeSelected);
	}

	private void loadResponsavelData(String responsavelCpf, String studentSelected, String typeSelected) {
    	DocumentReference responsavelRef = db.collection("responsavel").document(responsavelCpf);
		responsavelRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DocumentSnapshot> task) {
				if(task.isSuccessful()){
					DocumentSnapshot document = task.getResult();
					ResponsavelFirebase responsavel = document.toObject(ResponsavelFirebase.class);

					Requisicao requisicao = new Requisicao();

					requisicao.setResponsavelId(responsavel.getId());
					requisicao.setObservacao(request.getText().toString());
					requisicao.setDataRequisicao(date.getText().toString());
					requisicao.setTipoRequisicao(typeSelected);

					for (AlunoFirebase af : students){
						if (af.getNome().equals(studentSelected)){
							requisicao.setAlunoId(af.getId());
							requisicao.setEscolaId(af.getEscolaId());
						}
					}

					sendRequestFirebase(requisicao);
				}
			}
		});
	}

	private void sendRequestFirebase(Requisicao requisicao) {
		db.collection("requisicao").add(requisicao);
		Toast.makeText(getContext(),"Requisição Enviada",Toast.LENGTH_LONG).show();
		request.getText().clear();
		date.getText().clear();
	}

	private void setStudents(Bundle data) {
		DocumentReference docRef = db.collection("responsavel").document(data.getString("responsavelCpf"));
		docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DocumentSnapshot> task) {
				if (task.isSuccessful()){
					DocumentSnapshot document = task.getResult();
					ResponsavelFirebase responsavel = document.toObject(ResponsavelFirebase.class);
					students = responsavel.getAlunos();

					ArrayAdapter<CharSequence> adapterSpinner = new ArrayAdapter<CharSequence>(getContext(),android.R.layout.simple_spinner_item );

					for(int i = 0; i < students.size(); i++){
						adapterSpinner.add(students.get(i).getNome());
					}
					adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					studentsSpinner.setAdapter(adapterSpinner);
				}
			}
		});
	}
}
