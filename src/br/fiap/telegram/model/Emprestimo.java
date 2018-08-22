
package br.fiap.telegram.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Emprestimo implements Serializable {

	private static final long serialVersionUID = -2570104035411193374L;
	
	private LocalDateTime dataContratacao;
	private int qtdeMeses;
	private double valorContratado;
	private double valorEmprestimo;
	private Conta conta;

	public Emprestimo() {}

	public Emprestimo(double valorContratado, int qtdeMeses, Conta conta) {
		this.dataContratacao = LocalDateTime.now();
		this.qtdeMeses = qtdeMeses;
		this.valorContratado = valorContratado;
		this.valorEmprestimo = valorContratado + (valorContratado * 0.05 * qtdeMeses);
		this.conta = conta;
	}

	public LocalDateTime getDataContratacao() {
		return dataContratacao;
	}

	public String getDataContratacaoFormated() {

		return dataContratacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

	public void setDataContratacao(LocalDateTime dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public int getQtdeMeses() {
		return qtdeMeses;
	}

	public void setQtdeMeses(int qtdeMeses) {
		this.qtdeMeses = qtdeMeses;
	}

	public double getValorEmprestimo() {
		return valorEmprestimo;
	}

	public void setValorEmprestimo(double valorEmprestimo) {
		this.valorEmprestimo = valorEmprestimo;
	}

	public double getValorContratado() {
		return valorContratado;
	}

	public void setValorContratado(double valorContratado) {
		this.valorContratado = valorContratado;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getDataFinalEmprestimo() {
		LocalDateTime dataFinal = dataContratacao.plusMonths(getQtdeMeses());

		return dataFinal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
	}
}
