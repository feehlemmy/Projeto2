package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "classe")
@DynamicUpdate
public class Classe extends AbstractEntity<Long>{
	
	@Column(name = "nome", nullable = false, length = 50)
	private String nome;
	
	@Column(name = "ano_classe", nullable = false)
	private String anoClasse;
	
	@Column(name = "ano_letivo", nullable = false)
	private String anoLetivo;
	
	@Column(name = "turno", nullable = false)
	private String turno;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "escola_id")
	private Long escolaId;
	
	@Column(name = "professor_id")
	private Long professorId;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAnoClasse() {
		return anoClasse;
	}

	public void setAnoClasse(String anoClasse) {
		this.anoClasse = anoClasse;
	}

	public String getAnoLetivo() {
		return anoLetivo;
	}

	public void setAnoLetivo(String anoLetivo) {
		this.anoLetivo = anoLetivo;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Long getEscolaId() {
		return escolaId;
	}

	public void setEscolaId(Long escolaId) {
		this.escolaId = escolaId;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}
}
