package br.fiap.telegram.command;

import br.fiap.telegram.model.Cliente;

public class CommandFactory {

    public static Comando getComando(Cliente cliente) {

        ComandoEnum comando = ComandoEnum.getByCodigo(cliente.getMensagemAtual());

        if (comando == null) {
            comando = ComandoEnum.getByCodigo(cliente.getComandoAtual());
        }

        if (comando != null) {

            cliente.setComandoAtual(comando.codigo);

            switch (comando) {

                case START: {
                    return new ComandoStart();
                }

                case ABRIR_CONTA: {
                    return new ComandoAbrirConta();
                }

                case MODIFICAR_CONTA: {
                    return new ComandoModificarConta();
                }

                case INCLUIR_DEPENDENTE: {
                    return new ComandoIncluirDependentes();
                }

                case EXIBIR_DADOS: {
                    return new ComandoExibirDados();
                }

                case DEPOSITO: {
                    return new ComandoDeposito();
                }

                case SAQUE: {
                    return new ComandoSaque();
                }

                case EXTRATO: {
                    return new ComandoExtrato();
                }

                case EMPRESTIMO: {
                    return new ComandoEmprestimo();
                }

                case SALDO_DEVEDOR: {
                    return new ComandoSaldoDevedor();
                }

                case LANCAMENTOS: {
                    return new ComandoLancamentos();
                }

                case RETIRADAS: {
                    return new ComandoRetiradas();
                }

                case TARIFAS: {
                    return new ComandoTarifas();
                }

                case AJUDA: {
                    return new ComandoAjuda();
                }
            }

        }

        return new ComandoNaoEncontrado();


    }
}
