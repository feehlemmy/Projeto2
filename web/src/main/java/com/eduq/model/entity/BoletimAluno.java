package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "boletim_aluno")
@DynamicUpdate
public class BoletimAluno extends AbstractEntity<Long>{
	
	@Column(name = "boletim_id")
	private Long boletimId;
	
	@Column(name = "aluno_id")
	private Long AlunoId;

	public Long getBoletimId() {
		return boletimId;
	}

	public void setBoletimId(Long boletimId) {
		this.boletimId = boletimId;
	}

	public Long getAlunoId() {
		return AlunoId;
	}

	public void setAlunoId(Long alunoId) {
		AlunoId = alunoId;
	}
}
