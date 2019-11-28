package com.pinguimsoftware.gabrielsilva.eduqapp.model.entity;

public class AlunoChamada {
	private Long alunoId;
	private Long chamada_id;
	private Integer falta;
	private String status;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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


