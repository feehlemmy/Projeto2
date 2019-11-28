package com.pinguimsoftware.gabrielsilva.eduqapp.model.entity;
public class Evento {
	private String nomeEvento;
	private String dataInicioEvento;
	private String dataFimEvento;
	private String horaInicioEvento;
	private String descricaoEvento;
	private Long escolaId;
	public Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	public String getDataInicioEvento() {
		return dataInicioEvento;
	}

	public void setDataInicioEvento(String dataInicioEvento) { this.dataInicioEvento = dataInicioEvento; }

	public String getDataFimEvento() {
		return dataFimEvento;
	}

	public void setDataFimEvento(String dataFimEvento) {
		this.dataFimEvento = dataFimEvento;
	}

	public String getHoraInicioEvento() {
		return horaInicioEvento;
	}

	public void setHoraInicioEvento(String horaInicioEvento) { this.horaInicioEvento = horaInicioEvento; }

	public String getDescricaoEvento() {
		return descricaoEvento;
	}

	public void setDescricaoEvento(String descricaoEvento) {
		this.descricaoEvento = descricaoEvento;
	}

	public Long getEscolaId() {
		return escolaId;
	}

	public void setEscolaId(Long escolaId) {
		this.escolaId = escolaId;
	}
}
