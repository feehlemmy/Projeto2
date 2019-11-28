package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "escola")
@DynamicUpdate
public class Escola extends AbstractEntity<Long>{
	
	@Column(name = "codigo_mec", nullable = false, unique = true)
	private Integer codigoMec;
	
	@Column(name = "escola_nome", nullable = false, length = 50)
	private String nome;
	
	@Column(name = "email", nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(name = "senha", nullable = false, length = 50)
	private String senha;
	
	@Column(name = "diretor", nullable = false, length = 50)
	private String diretor;
	
	@Column(name = "telefone", length = 15)
	private String telefone;
	
	@Column(name = "cep", length = 10)
	private String cep;
	
	@Column(name = "endereco", length = 100)
	private String endereco;
	
	@Column(name = "municipio", length = 50)
	private String municipio;
	
	@Column(name = "uf", length = 2)
	private String uf;
	
	@Column(name = "logo", length = 100)
	private String logo;

	@Column(name = "confirmacao", nullable = false, length = 50)
	private String confirmacao;

	public Integer getCodigoMec() {
		return codigoMec;
	}

	public void setCodigoMec(Integer codigoMec) {
		this.codigoMec = codigoMec;
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

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getConfirmacao() {
		return confirmacao;
	}

	public void setConfirmacao(String confirmacao) {
		this.confirmacao = confirmacao;
	}
}
