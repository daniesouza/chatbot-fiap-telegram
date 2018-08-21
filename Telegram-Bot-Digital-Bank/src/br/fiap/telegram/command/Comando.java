package br.fiap.telegram.command;

import br.fiap.telegram.model.Cliente;

public interface Comando {

    void processar(Cliente cliente) throws Exception;
}
