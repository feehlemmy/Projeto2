package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "responsavel")
@DynamicUpdate
public class Responsavel extends Pessoa{
	@Column(name = "foto")
	private String foto;
	
	@Column(name = "pessoa_id")
	private Long pessoa_id;

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Long getPessoa_id() {
		return pessoa_id;
	}

	public void setPessoa_id(Long pessoa_id) {
		this.pessoa_id = pessoa_id;
	}
}
