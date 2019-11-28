package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "email")
public class Email extends AbstractEntity<Long>{
	
	@Column (name = "email_escola")
	private String emailEscola;
	
	@Column (name = "nome_escola")
	private String nomeEscola;
	
	@Column (name = "titulo_duvida")
	private String tituloDuvida;
	
	@Column (name = "descricao_duvida")
	private String descricaoDuvida;
	
	@Column (name = "email_professor")
	private String emailProfessor;
	
	@Column (name = "nome_professor")
	private String nomeProfessor;
	
	@Column (name = "codigo_professor")
	private String codigoProfessor;
	
	@Column (name = "senha_professor")
	private String senhaProfessor;
	
	@Column (name = "codigo_mec_escola")
	private Integer codigoMec;
	
	@Column (name = "enviado")
	private String enviado = "nao";
	
	@Column (name = "senha_escola")
	private String senhaEscola;

	@Column(name = "caracteristica")
	private String caracteristica;

	public String getEmailEscola() {
		return emailEscola;
	}

	public void setEmailEscola(String emailEscola) {
		this.emailEscola = emailEscola;
	}

	public String getNomeEscola() {
		return nomeEscola;
	}

	public void setNomeEscola(String nomeEscola) {
		this.nomeEscola = nomeEscola;
	}

	public String getTituloDuvida() {
		return tituloDuvida;
	}

	public void setTituloDuvida(String tituloDuvida) {
		this.tituloDuvida = tituloDuvida;
	}

	public String getDescricaoDuvida() {
		return descricaoDuvida;
	}

	public void setDescricaoDuvida(String descricaoDuvida) {
		this.descricaoDuvida = descricaoDuvida;
	}

	public String getEmailProfessor() {
		return emailProfessor;
	}

	public void setEmailProfessor(String emailProfessor) {
		this.emailProfessor = emailProfessor;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}

	public String getCodigoProfessor() {
		return codigoProfessor;
	}

	public void setCodigoProfessor(String codigoProfessor) {
		this.codigoProfessor = codigoProfessor;
	}

	public String getSenhaProfessor() {
		return senhaProfessor;
	}

	public void setSenhaProfessor(String senhaProfessor) {
		this.senhaProfessor = senhaProfessor;
	}

	public String getEnviado() {
		return enviado;
	}

	public void setEnviado(String enviado) {
		this.enviado = enviado;
	}

	public String getSenhaEscola() {
		return senhaEscola;
	}

	public void setSenhaEscola(String senhaEscola) {
		this.senhaEscola = senhaEscola;
	}

	public String getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}

	public Integer getCodigoMec() {
		return codigoMec;
	}

	public void setCodigoMec(Integer codigoMec) {
		this.codigoMec = codigoMec;
	}
}
