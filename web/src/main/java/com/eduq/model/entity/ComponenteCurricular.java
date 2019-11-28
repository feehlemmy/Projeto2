package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "componente")
@DynamicUpdate
public class ComponenteCurricular extends AbstractEntity<Long>{
	
	@Column(name = "nome", nullable = false, length = 50)
	private String nome;
	
	@Column(name = "carga_horaria", nullable = false, length = 6)
	private String cargaHoraria;
	
	@Column(name = "tipo_avaliacao", nullable = false, length = 16)
	private String tipoAvaliacao;
	
	@Column(name = "media_aprovacao", nullable = false, length = 3)
	private String mediaAprovacao;

	@Column(name = "escola_id")
	private Long escolaId;

	public String getMediaAprovacao() {
		return mediaAprovacao;
	}

	public void setMediaAprovacao(String mediaAprovacao) {
		this.mediaAprovacao = mediaAprovacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getTipoAvaliacao() {
		return tipoAvaliacao;
	}

	public void setTipoAvaliacao(String tipoAvaliacao) {
		this.tipoAvaliacao = tipoAvaliacao;
	}

	public Long getEscolaId() {
		return escolaId;
	}

	public void setEscolaId(Long escolaId) {
		this.escolaId = escolaId;
	}
}
