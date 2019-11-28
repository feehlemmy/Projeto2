package com.pinguimsoftware.gabrielsilva.eduqapp.model.entity;

import java.util.ArrayList;
import java.util.List;

public class ResponsavelFirebase {
	private Pessoa pessoa;
	private String cpf;
	private String nome;
	private String email;
	private String senha;
	private String telefone;
	private String endereco;
	private String municipio;
	private String uf;
	private String cep;
	private Long id;
	private String foto;
	private List<AlunoFirebase> alunos = new ArrayList<AlunoFirebase>();

	public List<AlunoFirebase> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<AlunoFirebase> alunos) {
		this.alunos = alunos;
	}


	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}
