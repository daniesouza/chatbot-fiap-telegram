
package br.fiap.telegram.utils;

import java.util.Collections;
import java.util.NoSuchElementException;

import br.fiap.telegram.comparator.ComparadorCliente;
import br.fiap.telegram.model.Cliente;

public class ListaCliente extends Lista<Cliente> {

	private static final long serialVersionUID = 115779705960850589L;

	public Cliente getByChatId(int chatId) {
		return super.lista.stream()
		        .filter(c -> c.getChatId() == chatId)
		        .sorted(Collections.reverseOrder(new ComparadorCliente()))
		        .findFirst()
		        .orElse(new Cliente());
	}

	public Cliente get(Cliente cliente) {
		return super.lista.stream()
		        .filter(c -> c.equals(cliente))
		        .sorted(Collections.reverseOrder(new ComparadorCliente()))
		        .findFirst()
		        .orElseThrow(NoSuchElementException::new);
	}

}
