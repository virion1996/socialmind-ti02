package model;

import java.sql.Date;

//package com.ti2cc;

public class Anotacao {
    private int Psicologo_ID;
    private int Anotacao_ID;
    private String Nome_anotacao;
    private String Motivo;
    private Date Criacao;
    private String Obs;
    private Date Data_consulta;

//  public Anotacao() {
//        this.Nome_anotacao = "";
//        this.Motivo = "";
//        this.Criacao = new Date();
//        this.Obs = "";
//        this.Data_consulta = new Date();
//    }

    public Anotacao(String nome_anotacao2, String motivo2, java.util.Date criacao2, java.util.Date data_consulta2,
			String obs2) {
    	this.Nome_anotacao = nome_anotacao2;
        this.Motivo = motivo2;
        this.Criacao = (java.sql.Date) criacao2;
        this.Data_consulta = (java.sql.Date) data_consulta2;
        this.Obs = obs2;
	}

	public int getPsicologoId() {
        return this.Psicologo_ID;
    }

    public int getAnotacaoId() {
        return this.Anotacao_ID;
    }

    public String getNome_anotacao() {
        return this.Nome_anotacao;
    }

    public String getMotivo() {
        return this.Motivo;
    }

    public Date getCriacao() {
        return this.Criacao;
    }

    public Date getDataConsulta() {
        return this.Data_consulta;
    }

    public String getObs() {
        return this.Obs;
    }

    public void setPsicologoId(int value) {
        this.Psicologo_ID = value;
    }

    public void setAnotacaoId(int value) {
        this.Anotacao_ID = value;
    }

    public void setNome_anotacao(String value) {
        this.Nome_anotacao = value;
    }

    public void setMotivo(String value) {
        this.Motivo = value;
    }

    public void setCriacao(Date value) {
        this.Criacao = value;
    }

    public void setDataConsulta(Date value) {
        this.Data_consulta = value;
    }

    public void setObs(String value) {
        this.Obs = value;
    }

    @Override
    public String toString() {
        return "Anotacao(" + this.Psicologo_ID + ", " + this.Anotacao_ID + ", " + this.Nome_anotacao + ", " + this.Motivo + ", " + this.Criacao +
        ", " + this.Data_consulta + ", " + this.Obs + ")";
    }

}
