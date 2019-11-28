package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "dia_letivo")
@DynamicUpdate
public class DiaLetivo extends AbstractEntity<Long>{
	
	@Column(name = "escola_id")
	private Long escolaId;
	
	@Column(name = "dia")
	private String diaLetivo;

	public Long getEscolaId() {
		return escolaId;
	}

	public void setEscolaId(Long escolaId) {
		this.escolaId = escolaId;
	}

	public String getDataAviso() {
		return diaLetivo;
	}

	public void setDataAviso(String dataAviso) {
		this.diaLetivo = dataAviso;
	}
}