package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "aluno_chamada")
@DynamicUpdate
public class AlunoChamada extends AbstractEntity<Long>{
	 
	@Column(name = "aluno_id", nullable = false)
	private Long alunoId;

    @Column(name = "chamada_id", nullable = false)
    private Long chamada_id;
     
    @Column(name = "falta", nullable = false)
    private Integer falta;
    
    @Column(name = "status")
    private String status;

	public Long getAlunoId() {
		return alunoId;
	}

	public void setAlunoId(Long alunoId) {
		this.alunoId = alunoId;
	}

	public Long getChamada_id() {
		return chamada_id;
	}

	public void setChamada_id(Long chamada_id) {
		this.chamada_id = chamada_id;
	}

	public Integer getFalta() {
		return falta;
	}

	public void setFalta(Integer falta) {
		this.falta = falta;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
