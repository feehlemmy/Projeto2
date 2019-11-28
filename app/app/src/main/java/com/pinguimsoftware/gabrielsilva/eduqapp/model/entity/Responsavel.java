package com.pinguimsoftware.gabrielsilva.eduqapp.model.entity;


import java.util.ArrayList;
import java.util.List;

public class Responsavel extends Pessoa {


	private Pessoa pessoa;
	private String foto;
	private List<Aluno> alunos = new ArrayList<Aluno>();
	private Long id;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}



	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

}