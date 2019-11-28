
package com.pinguimsoftware.gabrielsilva.eduqapp.model.entity;

public class Aviso {

	private String tituloAviso;
	private String anoClasse;
	private Long classeId;
	private Long escolaId;
	private String dataAviso;
	private String tipoAviso;
	private String conteudo;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

}
