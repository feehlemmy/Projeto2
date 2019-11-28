package com.eduq.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "chamada")
@DynamicUpdate
public class Chamada extends AbstractEntity<Long>{
	
	@Column(name = "data_chamada", nullable = false)
	private String dataChamada;
	
	@Column(name = "escola_id", nullable = false)
	private Long idEscola;
	
	@Column(name = "classe_id", nullable = false)
	private Long idClasse;
	
	@Column(name = "realizada")
	private String realizada;

	@Transient
	private List<AlunoChamada> listaChamadas = new ArrayList<AlunoChamada>();
	
	public String getDataChamada() {
		return dataChamada;
	}

	public void setDataChamada(String dataChamada) {
		this.dataChamada = dataChamada;
	}

	public Long getIdEscola() {
		return idEscola;
	}

	public void setIdEscola(Long idEscola) {
		this.idEscola = idEscola;
	}

	public Long getIdClasse() {
		return idClasse;
	}

	public void setIdClasse(Long idClasse) {
		this.idClasse = idClasse;
	}

	public List<AlunoChamada> getListaChamadas() {
		return listaChamadas;
	}

	public void setListaChamadas(List<AlunoChamada> listaChamadas) {
		this.listaChamadas = listaChamadas;
	}

	public String getRealizada() {
		return realizada;
	}

	public void setRealizada(String realizada) {
		this.realizada = realizada;
	}
}
