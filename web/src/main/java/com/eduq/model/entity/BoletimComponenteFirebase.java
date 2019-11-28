package com.eduq.model.entity;

@SuppressWarnings("serial")
public class BoletimComponenteFirebase extends AbstractEntity<Long>{
	private String nomeComponente;
	private String mediaAprovacao;
	private String conceito;
	
	public String getNomeComponente() {
		return nomeComponente;
	}
	public void setNomeComponente(String nomeComponente) {
		this.nomeComponente = nomeComponente;
	}
	public String getMediaAprovacao() {
		return mediaAprovacao;
	}
	public void setMediaAprovacao(String mediaAprovacao) {
		this.mediaAprovacao = mediaAprovacao;
	}
	public String getConceito() {
		return conceito;
	}
	public void setConceito(String conceito) {
		this.conceito = conceito;
	}
}
