package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "boletim")
@DynamicUpdate
public class Boletim extends AbstractEntity<Long>{
	
    @Column(name = "classe_id")
    private Long idClasse;
    
    @Column(name = "escola_id")
    private Long idEscola;
    
    @Column(name = "bimestre")
    private String bimestre;
    
    @Column(name = "situacao")
    private String situacao;
    
    @Column(name = "observacao")
    private String observacao;
    
    @Column(name = "data_boletim")
    private String dataBoletim;
    
    @Column(name = "individual_criado")
    private String individualCerado;

	public String getBimestre() {
		return bimestre;
	}

	public void setBimestre(String bimestre) {
		this.bimestre = bimestre;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getDataBoletim() {
		return dataBoletim;
	}

	public void setDataBoletim(String dataBoletim) {
		this.dataBoletim = dataBoletim;
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

	public String getIndividualCerado() {
		return individualCerado;
	}

	public void setIndividualCerado(String individualCerado) {
		this.individualCerado = individualCerado;
	}
}
