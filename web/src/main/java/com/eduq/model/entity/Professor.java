package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "professor")
@PrimaryKeyJoinColumn(name = "pessoa_id")
@DynamicUpdate
public class Professor extends Pessoa {
	
	@Column(name = "codigo_gerado_escola", nullable = false, length = 8)
	private String codigoGeradoEscola;
	
	@Column(name = "escola_id", nullable = false)
	private Long escolaId;

	@Column(name = "senha_gerada", nullable = false)
	private String senhaGerada;
	
	@Transient
	private Pessoa pessoa;

	public String getCodigoGeradoEscola() {
		return codigoGeradoEscola;
	}

	public void setCodigoGeradoEscola(String codigoGeradoEscola) {
		this.codigoGeradoEscola = codigoGeradoEscola;
	}

	public String getSenhaGerada() {
		return senhaGerada;
	}

	public void setSenhaGerada(String senhaGerada) {
		this.senhaGerada = senhaGerada;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Long getEscolaId() {
		return escolaId;
	}

	public void setEscolaId(Long escolaId) {
		this.escolaId = escolaId;
	}
}
