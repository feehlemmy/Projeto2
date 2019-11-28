package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
	private Button btnAvisos;
	private Button btnEventos;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, null);
		Bundle data = getArguments();
		String cpfResponsavel = data.getString("responsavelCpf");

		btnAvisos = (Button) view.findViewById(R.id.btn_avisos);
		btnEventos = (Button) view.findViewById(R.id.btn_eventos);

		btnAvisos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeFragment.this.getContext(), ListAvisoActivity.class);
				intent.putExtra("cpfResponsavel", cpfResponsavel);
				startActivity(intent);
			}
		});

		btnEventos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeFragment.this.getContext(), ListEventosActivity.class);
				intent.putExtra("cpfResponsavel", cpfResponsavel);
				startActivity(intent);
			}
		});

		return view;
	}
}