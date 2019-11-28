package com.pinguimsoftware.gabrielsilva.eduqapp.model.entity;



public class Requisicao extends AbstractEntity<Long> {
	private Long escolaId;
	private Long responsavelId;
	private Long alunoId;
	private String tipoRequisicao;
	private String observacao;
	private String dataRequisicao;

	public Long getEscolaId() {
		return escolaId;
	}

	public void setEscolaId(Long escolaId) {
		this.escolaId = escolaId;
	}

	public Long getResponsavelId() {
		return responsavelId;
	}

	public void setResponsavelId(Long responsavelId) {
		this.responsavelId = responsavelId;
	}

	public Long getAlunoId() {
		return alunoId;
	}

	public void setAlunoId(Long alunoId) {
		this.alunoId = alunoId;
	}

	public String getTipoRequisicao() {
		return tipoRequisicao;
	}

	public void setTipoRequisicao(String tipoRequisicao) {
		this.tipoRequisicao = tipoRequisicao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getDataRequisicao() {
		return dataRequisicao;
	}

	public void setDataRequisicao(String dataRequisicao) {
		this.dataRequisicao = dataRequisicao;
	}
}
