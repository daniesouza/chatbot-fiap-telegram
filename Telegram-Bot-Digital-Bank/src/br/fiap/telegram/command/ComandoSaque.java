package br.fiap.telegram.command;

import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.manager.SessionManager;
import br.fiap.telegram.model.Cliente;

public class ComandoSaque implements Comando {


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

        boolean isComando = ComandoEnum.getByCodigo(cliente.getMensagemAtual()) != null;

        if (cliente.getEstadoAtual() == null || "".equals(cliente.getEstadoAtual()) || isComando) {
            cliente.setMensagemAtual("");
            cliente.setEstadoAtual("informar_valor");
            SessionManager.addClient(cliente);
        }

        sacar(cliente);

    }

    private boolean clientePossuiConta(Cliente cliente) {
        return cliente.getConta() != null;
    }


    private void sacar(Cliente cliente) {

        if (cliente.getMensagemAtual().trim().equals("")) {
            BotManager.enviarMensagem(cliente.getChatId(), "Informe o valor a ser sacado (será cobrada uma taxa de R$ 2,50)");
            return;
        }

        try {
            Double valor = Double.parseDouble(cliente.getMensagemAtual());

            if(cliente.getConta().sacar(valor)){
                BotManager.enviarMensagem(cliente.getChatId(), "Saque efetuado com sucesso! :)\nSaldo atual: R$ " + cliente.getConta().getSaldo());
            }else{
                BotManager.enviarMensagem(cliente.getChatId(), "Saldo insuficiente para o saque! :(\nSaldo atual: R$ " + cliente.getConta().getSaldo());
            }

            cliente.setMensagemAtual(null);
            cliente.setEstadoAtual(null);
            cliente.setComandoAtual(null);
            
            SessionManager.addClient(cliente);

            ComandoStart.mostrarMenu(cliente);

        } catch (Exception e) {
            BotManager.enviarMensagem(cliente.getChatId(), "Valor inválido! Tente novamente!");
        }
    }
}
