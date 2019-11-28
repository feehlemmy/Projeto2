package com.pinguimsoftware.gabrielsilva.eduqapp.model.entity;

public class Chamada {
    private Long id;
    private Long idClasse;
    private Long idEscola;
    private String dataChamada;
    private String realizada;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getIdClasse() { return idClasse; }

    public void setIdClasse(Long idClasse) { this.idClasse = idClasse; }

    public Long getIdEscola() { return idEscola; }

    public void setIdEscola(Long idEscola) { this.idEscola = idEscola; }

    public String getDataChamada() { return dataChamada; }

    public void setDataChamada(String dataChamada) { this.dataChamada = dataChamada; }

    public String getRealizada() { return realizada; }

    public void setRealizada(String realizada) { this.realizada = realizada; }
}
