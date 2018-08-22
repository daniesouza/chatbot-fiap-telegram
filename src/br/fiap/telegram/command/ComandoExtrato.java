package br.fiap.telegram.command;

import java.util.List;

import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.manager.SessionManager;
import br.fiap.telegram.model.Cliente;
import br.fiap.telegram.model.Lancamento;

public class ComandoExtrato implements Comando {


    @Override
    public void processar(Cliente cliente) throws Exception {

        if (!clientePossuiConta(cliente)) {
            BotManager.enviarMensagem(cliente.getChatId(), "Você não possui conta cadastrada!" +
                    "\nCrie uma conta antes de continuar. /abrirconta");
            cliente.setComandoAtual(null);
            SessionManager.addClient(cliente);
            ComandoStart.mostrarMenu(cliente);
            return;
        }


        exibirExtrato(cliente);

    }

    private boolean clientePossuiConta(Cliente cliente) {
        return cliente.getConta() != null;
    }


    private void exibirExtrato(Cliente cliente) throws Exception {

        List<Lancamento> extrato = cliente.getConta().extrato();

        if (!extrato.isEmpty()) {

            StringBuilder sb = new StringBuilder();

            for (Lancamento lc : extrato) {
                sb.append(lc.getExtrato());
                sb.append("\n");
            }

            sb.append("Saldo atual: R$ " + cliente.getConta().getSaldo());

            BotManager.enviarMensagem(cliente.getChatId(), sb.toString());

        } else {

            if (cliente.getConta().getSaldo() < 1f) {
                BotManager.enviarMensagem(cliente.getChatId(), "Saldo insuficiente para o extrato! :(\nSaldo atual: R$ " + cliente.getConta().getSaldo());
            } else {
                BotManager.enviarMensagem(cliente.getChatId(), "Não há informções para exibir");
            }
        }


        cliente.setMensagemAtual(null);
        cliente.setEstadoAtual(null);
        cliente.setComandoAtual(null);
        
        SessionManager.addClient(cliente);

        ComandoStart.mostrarMenu(cliente);

    }
}
