package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "aviso")
@DynamicUpdate
public class Aviso extends AbstractEntity<Long>{
	
	@Column(name = "titulo_aviso")
	private String tituloAviso;
	
	@Column(name = "ano_classe")
	private String anoClasse;
	
	@Column(name = "classeId")
	private Long classeId;
	
	@Column(name = "escolaId")
	private Long escolaId;
	
	@Column(name = "data_aviso")
	private String dataAviso;
	
	@Column(name = "tipo_aviso")
	private String tipoAviso;
	
	@Column(name = "conteudo_aviso")
	private String conteudo;
	
	@Column(name = "aluno_id")
	private Long alunoId;

	public String getTituloAviso() {
		return tituloAviso;
	}

	public void setTituloAviso(String tituloAviso) {
		this.tituloAviso = tituloAviso;
	}

	public String getAnoClasse() {
		return anoClasse;
	}

	public void setAnoClasse(String anoClasse) {
		this.anoClasse = anoClasse;
	}

	public Long getClasseId() {
		return classeId;
	}

	public void setClasseId(Long classeId) {
		this.classeId = classeId;
	}

	public Long getEscolaId() {
		return escolaId;
	}

	public void setEscolaId(Long escolaId) {
		this.escolaId = escolaId;
	}

	public String getDataAviso() {
		return dataAviso;
	}

	public void setDataAviso(String dataAviso) {
		this.dataAviso = dataAviso;
	}

	public String getTipoAviso() {
		return tipoAviso;
	}

	public void setTipoAviso(String tipoAviso) {
		this.tipoAviso = tipoAviso;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Long getAlunoId() {
		return alunoId;
	}

	public void setAlunoId(Long alunoId) {
		this.alunoId = alunoId;
	}
}
