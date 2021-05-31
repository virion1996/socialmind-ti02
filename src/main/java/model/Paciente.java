package model;

import java.util.Date;

//package com.ti2cc;

public class Paciente {
    private int id;
    private int anonimo;
    private String cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String sobre;
    private Date criacao;
    private Date nascimento;
    private String sexo;
    private String telefone;
    private String cep;
    private String valor;
    private String senha;
	
	public Paciente(
        String nome,
        String sobrenome,
        int anonimo,
        String Sexo,
        String telefone,
        String cep,
        String valor,
        String senha,
        String cpf,
        String email,
        String sobre,
        Date criacao,
        Date nascimento

    ) {
        this.nome = nome;
        this.sobrenome = sobrenome;
		this.anonimo = anonimo;
        this.sexo = Sexo;
        this.telefone = telefone;
        this.cep = cep;
        this.valor = valor;
        this.senha = senha;
		this.cpf = cpf;
		this.email = email;
		this.sobre = sobre;
		this.criacao = criacao;
		this.nascimento = nascimento;
	}

	public int getId() { return this.id; }
    public int getAnonimo() { return this.anonimo; }
    public String getCpf() { return this.cpf; }
    public String getNome() { return this.nome; }
    public String getSobrenome() { return this.sobrenome; }
    public String getEmail() { return this.email; }
    public String getSobre() { return this.sobre; }
    public Date getCriacao() { return this.criacao; }
    public Date getNascimento() { return this.nascimento; }
    public String getSexo() {return this.sexo;}
    public String getTelefone() {return this.telefone;}
    public String getCEP() {return this.cep;}
    public String getSenha() {return this.senha;}
    public String getValor() {return this.valor;}

    public void setId(int value) { this.id = value; }
    public void setAnonimo(int value) { this.anonimo = value; }
    public void setCpf(String value) { this.cpf = value; }
    public void setNome(String value) { this.nome = value; }
    public void setSobenome(String value) { this.sobrenome = value; }
    public void setEmail(String value) { this.email = value; }
    public void setSobre(String value) { this.sobre = value; }
    public void setCriacao(Date value) { this.criacao = value; }
    public void setNascimento(Date value) { this.nascimento = value; }
    public void setSexo(String value) {this.sexo = value;}
    public void setTelefone(String value) {this.telefone = value;}
    public void setCEP(String value) {this.cep = value;}
    public void setSenha(String value) {this.senha = value;}
    public void setValor(String value) {this.valor = value;}

	@Override
	public String toString() {
		return "Paciente("+this.id+", "+this.anonimo+", "+this.cpf+", "+
            this.nome+", "+this.sobrenome+", "+this.email+", "+
            this.sobre+", "+this.criacao+", "+this.nascimento+")";
	}
	
}
