package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.AlunoFirebase;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.ResponsavelFirebase;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private CardView cardViewEdit;
    private CardView cardViewAbout;
    private TextView username;
    private EditText codigoAluno;
    private Button btn_add_aluno;
    private List<AlunoFirebase> students = null;
    private AlunoFirebase student = null;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        Bundle data = getArguments();
        String name = data.getString("name");
        String cpfResponsavel = data.getString("responsavelCpf");

        cardViewEdit = (CardView) view.findViewById(R.id.card_edit);
        cardViewAbout = (CardView) view.findViewById(R.id.card_about);
        username = view.findViewById(R.id.lbl_username);
        codigoAluno = view.findViewById(R.id.codigo_aluno);
        btn_add_aluno = view.findViewById(R.id.btn_add_aluno);

        username.setText(name);

        btn_add_aluno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setStudents(codigoAluno, cpfResponsavel);
            }
        });

        cardViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileFragment.this.getContext(), ProfileEditActivity.class);
                startActivity(intent);
            }
        });

        cardViewAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileFragment.this.getContext(), ListAlunoActivity.class);
                intent.putExtra("cpfResponsavel", cpfResponsavel);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setStudents(EditText codigoAluno, String cpfResponsavel) {
        students = new ArrayList<>();
        student = new AlunoFirebase();

        DocumentReference docRef = db.collection("responsavel").document(cpfResponsavel);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    ResponsavelFirebase responsavel = document.toObject(ResponsavelFirebase.class);
                    students = responsavel.getAlunos();

                    if(TextUtils.isEmpty(codigoAluno.getText())) {
                        codigoAluno.setError("Código do Aluno Obrigatorio");
                        codigoAluno.setFocusable(true);
                    }else {
                        String code = codigoAluno.getText().toString();
                        student.setCodigoGeradoEscola(code);
                        students.add(student);
                        buildStudentList(students, cpfResponsavel);
                    }
                }
            }
        });
    }

    private void buildStudentList(List<AlunoFirebase> students, String cpfResponsavel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference responsavelRef = db.collection("responsavel").document(cpfResponsavel);
        responsavelRef.update("alunos", students);

        Toast.makeText(getContext(), "Aluno adicionado!", Toast.LENGTH_SHORT).show();
        codigoAluno.getText().clear();
    }
}
