
package br.fiap.telegram.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Conta implements Serializable {

	private static final long serialVersionUID = -8657794166473065263L;

	// Informações Cadastrais da conta
	private int numAg;
	private int numCc;
	private List<Lancamento> lancamentos = new ArrayList<>();
	private List<Emprestimo> emprestimos = new ArrayList<>();
	
	private transient Emprestimo emprestimo = new Emprestimo(); // emprestimo usado para manter memoria

	// Informações Financeiras da conta
	private double saldo;

	public Conta() {
		this.numAg = new Random().nextInt(9999);
		this.numCc = new Random().nextInt(999999);
		this.lancamentos = new ArrayList<>();
		this.emprestimos = new ArrayList<>();
		this.saldo = 0f;
	}

	public int getNumAg() {
		return numAg;
	}

	public void setNumAg(int numAg) {
		this.numAg = numAg;
	}

	public int getNumCc() {
		return numCc;
	}

	public void setNumCc(int numCc) {
		this.numCc = numCc;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamento(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public void addLancamento(Lancamento lancamento) {
		this.lancamentos.add(lancamento);
	}

	public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

	public void addEmprestimo(Emprestimo emprestimo) {
		depositar("Depósito Empréstimo", emprestimo.getValorContratado());
		this.emprestimos.add(emprestimo);
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	 public Emprestimo getEmprestimo() {
	 return emprestimo;
	 }
	
	 public void setEmprestimo(Emprestimo emprestimo) {
	 this.emprestimo = emprestimo;
	 }

	public synchronized void depositar(double valor) {
		depositar("Depósito", valor);
	}

	private synchronized void depositar(String descricao, double valor) {
		this.lancamentos.add(new Lancamento(descricao, valor, 0f, LocalDateTime.now(), this));
		this.saldo += valor;
	}

	public synchronized boolean sacar(double valor) {
		return sacar("Saque", valor, 2.50);
	}

	private synchronized boolean sacar(String descricao, double valor, double tarifa) {

		if ((saldo - valor - tarifa) >= 0f) {

			this.saldo -= valor + tarifa;
			this.lancamentos.add(new Lancamento(descricao, valor, tarifa, LocalDateTime.now(), this));

			return true;

		} else {
			return false;
		}
	}

	public List<Lancamento> extrato() {

		List<Lancamento> extrato = new ArrayList<>();

		if (sacar("Extrato", 0f, 1f)) {

			extrato = lancamentos.stream()
			        .filter(o -> o.getDescricao().startsWith("Saque")
			                || o.getDescricao().startsWith("Depósito")
			                || o.getDescricao().startsWith("Extrato"))
			        .collect(Collectors.toList());
		}

		return extrato;
	}

	public List<Lancamento> retiradas() {

		return lancamentos.stream().filter(o -> o.getDescricao().startsWith("Saque")).collect(Collectors.toList());

	}

	public List<Lancamento> tarifas() {

		return lancamentos.stream().filter(o -> o.getTarifa() > 0f).collect(Collectors.toList());

	}
}
