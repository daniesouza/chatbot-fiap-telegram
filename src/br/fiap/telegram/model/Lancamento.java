
package br.fiap.telegram.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lancamento implements Serializable {

	private static final long serialVersionUID = 923943277529666671L;
	
	private String descricao;
	private double valor;
	private double tarifa;
	private LocalDateTime dataHora;
	private Conta conta;

	public Lancamento(String descricao, double valor, double tarifa, LocalDateTime dataHora, Conta conta) {
		this.descricao = descricao;
		this.valor = valor;
		this.tarifa = tarifa;
		this.dataHora = dataHora;
		this.conta = conta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getTarifa() {
		return tarifa;
	}

	public void setTarifa(double tarifa) {
		this.tarifa = tarifa;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getExtrato() {
		return getDataFormated() + " - " + descricao + ": " + valor + " (" + tarifa + ")";
	}

	public String getDataFormated() {

		return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

	public String getRetirada() {
		return dataHora + " - " + valor;
	}

	public String getTarifaServico() {
		return dataHora + " - " + tarifa;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

}
