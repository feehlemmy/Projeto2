package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "boletim_componente")
@DynamicUpdate
public class BoletimComponente extends AbstractEntity<Long>{
	
	@Column(name = "componente_id")
	private Long componenteId;
	
	@Column(name = "boletim_id")
	private Long boletimId;

	public Long getComponenteId() {
		return componenteId;
	}

	public void setComponenteId(Long componenteId) {
		this.componenteId = componenteId;
	}

	public Long getBoletimId() {
		return boletimId;
	}

	public void setBoletimId(Long boletimId) {
		this.boletimId = boletimId;
	}
}
