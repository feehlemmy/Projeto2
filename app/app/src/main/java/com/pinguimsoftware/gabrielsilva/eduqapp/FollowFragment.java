package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.AlunoFirebase;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.ResponsavelFirebase;

import java.util.List;

public class FollowFragment extends Fragment {
    private List<AlunoFirebase> students = null;
    private Button btnReportCard;
    private Button btnCalendar;
    private Spinner studentsSpinner;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, null);
        Bundle data = getArguments();
        btnReportCard =  view.findViewById(R.id.btn_report);
        btnCalendar = view.findViewById(R.id.btn_calendar);
        studentsSpinner = view.findViewById(R.id.follow_spinner);

        setStudents(data);

        btnReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentSelected = studentsSpinner.getSelectedItem().toString();
                Intent intent = new Intent(getContext(), ReportCardActivity.class);

                intent.putExtra("alunoNome", studentSelected);
                intent.putExtra("cpfResponsavel", data.getString("responsavelCpf"));
                startActivity(intent);
            }
        });
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentSelected = studentsSpinner.getSelectedItem().toString();
                Intent intent = new Intent(getContext(), CalendarActivity.class);

                intent.putExtra("alunoNome", studentSelected);
                intent.putExtra("cpfResponsavel", data.getString("responsavelCpf"));
                startActivity(intent);
            }
        });
        return view;
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
