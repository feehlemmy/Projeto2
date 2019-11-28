package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicUpdate
public class Pessoa extends AbstractEntity<Long> {
	
	@Column(name = "cpf", nullable = false, length = 15)
	private String cpf;
	
	@Column(name = "nome", nullable = false, length = 50)
	private String nome;
	
	@Column(name = "email", length = 50)
	private String email;
	
	@Column(name = "senha", length = 50)
	private String senha;
	
	@Column(name = "telefone", length = 15)
	private String telefone;
	
	@Column(name = "endereco", length = 100)
	private String endereco;
	
	@Column(name = "municipio", length = 50)
	private String municipio;
	
	@Column(name = "uf", length = 2)
	private String uf;
	
	@Column(name = "cep", length = 10)
	private String cep;

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
