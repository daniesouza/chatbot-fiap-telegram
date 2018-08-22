
package br.fiap.telegram.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Dependente extends Pessoa implements Serializable {

	private static final long serialVersionUID = -2274964378770381056L;

	private Cliente cliente;

	public Dependente() {}

	public Dependente(String nome, long cpf, String rg, LocalDate dataNascimento, Cliente cliente) {
		super(nome, cpf, rg, dataNascimento);
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
