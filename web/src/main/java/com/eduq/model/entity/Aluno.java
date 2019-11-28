package com.eduq.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "aluno")
@PrimaryKeyJoinColumn(name = "pessoa_id")
@DynamicUpdate
public class Aluno extends Pessoa{
	
	@Column(name = "matricula", nullable = false, length = 10)
	private String matricula;
	
	@Column(name = "data_matricula", nullable = false, length = 10)
	private String dataMatricula;
	
	@Column(name = "codigo_gerado_escola", nullable = false, length = 8)
	private String codigoGeradoEscola;
	
	@Column(name = "escola_id", nullable = false)
	private Long escolaId;
	
	@Column(name = "pessoa_id", insertable = false, updatable = false)
	private Long pessoaId;
	
	@Column(name = "nome_pai", length = 50)
	private String nomePai;
	
	@Column(name = "nome_mae", nullable = false, length = 50)
	private String nomeMae;
	
	@Column(name = "situacao", nullable = false)
	private String situacao;
	
	@Column(name = "justificativa")
	private String justificativa;
	
	@Column(name = "data_nascimento", nullable = false, length = 10)
	private String dataNascimento;
	
	@Column(name = "cpf_responsavel", nullable = false, length = 15)
	private String cpfResponsavel;
	
	@Column(name = "nome_responsavel", length = 50)
	private String nomeResponsavel;
	
	@Transient
	private Pessoa pessoa;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getDataMatricula() {
		return dataMatricula;
	}

	public void setDataMatricula(String dataMatricula) {
		this.dataMatricula = dataMatricula;
	}

	public String getCodigoGeradoEscola() {
		return codigoGeradoEscola;
	}

	public void setCodigoGeradoEscola(String codigoGeradoEscola) {
		this.codigoGeradoEscola = codigoGeradoEscola;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpfResponsavel() {
		return cpfResponsavel;
	}

	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public Long getEscolaId() {
		return escolaId;
	}

	public void setEscolaId(Long escolaId) {
		this.escolaId = escolaId;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Long getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}
}
