package br.fiap.telegram.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Lista<T> implements Serializable {

	private static final long serialVersionUID = 2108872808565294804L;
	
	protected List<T> lista = new ArrayList<>();

	public void adicionar(T entidade) {
		lista.add(entidade);
	}

	public T get(int pos) {
		return lista.get(pos);
	}

	public List<T> listar() {
		return lista;
	}

}
