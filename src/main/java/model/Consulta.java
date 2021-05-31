package model;

import java.sql.Date;
import java.sql.Time;

//package com.ti2cc;

public class Consulta {
    private int Psicologo_ID;
    private int Consulta_ID;
    private String Titulo;
    private String Descricao;
    private Date criacao;
    private Date data;
    private Time Tempo_inicial;
    private Time Tempo_final;

//    public Consulta() {
//		this.Titulo = "";
//		this.Descricao = "";
//		this.criacao = new Date();
//		this.data = new Date();
//        this.Tempo_inicial = new Timer();
//        this.Tempo_final = new Timer();
//	}

    public Consulta(String Titulo, String Descricao, java.util.Date criacao2, java.util.Date data2, Time tempo_inicial, Time tempo_final) {
        this.Titulo = Titulo;
        this.Descricao = Descricao;
        this.criacao = (Date) criacao2;
        this.data = (Date) data2;
        this.Tempo_inicial = tempo_inicial;
        this.Tempo_final = tempo_final;
    }
    
	public int getPsicologoId() {
        return this.Psicologo_ID;
    }

    public int getConsultaId() {
        return this.Consulta_ID;
    }

    public String getTitulo() {
        return this.Titulo;
    }

    public String getDescricao() {
        return this.Descricao;
    }

    public Date getCriacao() {
        return this.criacao;
    }

    public Date getData() {
        return this.data;
    }

    public Time getTempoInicial() {
        return this.Tempo_inicial;
    }

    public Time getTempoFinal() {
        return this.Tempo_final;
    }

    public void setPsicologoId(int value) {
        this.Psicologo_ID = value;
    }

    public void setConsultaId(int value) {
        this.Consulta_ID = value;
    }

    public void setTitulo(String value) {
        this.Titulo = value;
    }

    public void setDescricao(String value) {
        this.Descricao = value;
    }

    public void setCriacao(Date value) {
        this.criacao = value;
    }

    public void setData(Date value) {
        this.data = value;
    }

    public void setTempoInicial(Time value) {
        this.Tempo_inicial = value;
    }

    public void setTempoFinal(Time value) {
        this.Tempo_final = value;
    }

    @Override
    public String toString() {
        return "Consulta(" + this.Psicologo_ID + ", " + this.Consulta_ID + ", " + this.Titulo + ", " + this.Descricao
                + ", " + this.criacao + ", " + this.data + ", " + this.Tempo_inicial + ", " + this.Tempo_final + ")";
    }

}

