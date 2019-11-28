package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.ResponsavelFirebase;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ResponsavelFirebase responsavel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("responsavel")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()) {
                        for(DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            System.out.println(document.getData());
                            responsavel = document.toObject(ResponsavelFirebase.class);
                            loadFragment(new HomeFragment());

                        }
                    }
                }
            });

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(MainActivity.this);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            Bundle bundle = new Bundle();
            bundle.putString("name", responsavel.getNome());
            bundle.putString("responsavelCpf", responsavel.getCpf());

            Context context = this;
            SharedPreferences sharedPref = context.getSharedPreferences("user_preferences",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("email", responsavel.getEmail());
            editor.putString("cpf", responsavel.getCpf());
            editor.putString("nome", responsavel.getNome());
            editor.putBoolean("usuario_logado", true);

            editor.apply();

            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_follow:
                fragment = new FollowFragment();
                break;

            case R.id.navigation_request:
                fragment = new RequestFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
