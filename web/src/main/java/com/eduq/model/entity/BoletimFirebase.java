package com.eduq.model.entity;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class BoletimFirebase extends AbstractEntity<Long>{
	private Integer faltas;
    private String situacao;
    private Long alunoId;
    private Long boletimId;
    private String nomeAluno;
    private String matrícula;
    private Long boletimAlunoId;
    private List<BoletimComponenteFirebase> componentes = new ArrayList<BoletimComponenteFirebase>();
    
	public Integer getFaltas() {
		return faltas;
	}
	public void setFaltas(Integer faltas) {
		this.faltas = faltas;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Long getAlunoId() {
		return alunoId;
	}
	public void setAlunoId(Long alunoId) {
		this.alunoId = alunoId;
	}
	public Long getBoletimId() {
		return boletimId;
	}
	public void setBoletimId(Long boletimId) {
		this.boletimId = boletimId;
	}
	public String getNomeAluno() {
		return nomeAluno;
	}
	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}
	public String getMatrícula() {
		return matrícula;
	}
	public void setMatrícula(String matrícula) {
		this.matrícula = matrícula;
	}
	public Long getBoletimAlunoId() {
		return boletimAlunoId;
	}
	public void setBoletimAlunoId(Long boletimAlunoId) {
		this.boletimAlunoId = boletimAlunoId;
	}
	public List<BoletimComponenteFirebase> getComponentes() {
		return componentes;
	}
	public void setComponentes(List<BoletimComponenteFirebase> componentes) {
		this.componentes = componentes;
	}
}
