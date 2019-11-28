package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "evento")
@DynamicUpdate
public class Evento extends AbstractEntity<Long>{
	@Column(name = "nome_evento", nullable = false, length = 50)
	private String nomeEvento;
	
	@Column(name = "data_inicio", nullable = false)
	private String dataInicioEvento;
    
	@Column(name = "data_fim", nullable = false)
	private String dataFimEvento;
    
	@Column(name = "horario_inicio", nullable = false)
	private String horaInicioEvento;
    
	@Column(name = "descricao_evento", nullable = false)
	private String descricaoEvento;
	
	@Column(name = "escola_id")
	private Long escolaId;

	public String getNomeEvento() {
		return nomeEvento;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	public String getDataInicioEvento() {
		return dataInicioEvento;
	}

	public void setDataInicioEvento(String dataInicioEvento) {
		this.dataInicioEvento = dataInicioEvento;
	}

	public String getDataFimEvento() {
		return dataFimEvento;
	}

	public void setDataFimEvento(String dataFimEvento) {
		this.dataFimEvento = dataFimEvento;
	}

	public String getHoraInicioEvento() {
		return horaInicioEvento;
	}

	public void setHoraInicioEvento(String horaInicioEvento) {
		this.horaInicioEvento = horaInicioEvento;
	}

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
