package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "boletim_aluno_componente")
@DynamicUpdate
public class BoletimAlunoComponente extends AbstractEntity<Long>{
	
	@Column(name = "boletim_aluno_id")
	private Long boletimAlunoId;
	
	@Column(name = "boletim_componente_id")
	private Long boletimComponenteId;

	@Column(name = "faltas")
	private Integer faltas;
    
	@Column(name = "conceito")
	private String conceito;

	public Long getBoletimAlunoId() {
		return boletimAlunoId;
	}

	public void setBoletimAlunoId(Long boletimAlunoId) {
		this.boletimAlunoId = boletimAlunoId;
	}

	public Long getBoletimComponenteId() {
		return boletimComponenteId;
	}

	public void setBoletimComponenteId(Long boletimComponenteId) {
		this.boletimComponenteId = boletimComponenteId;
	}

	public Integer getFaltas() {
		return faltas;
	}

	public void setFaltas(Integer faltas) {
		this.faltas = faltas;
	}

	public String getConceito() {
		return conceito;
	}

	public void setConceito(String conceito) {
		this.conceito = conceito;
	}
}
