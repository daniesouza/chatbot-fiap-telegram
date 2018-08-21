package br.fiap.telegram.command;

import java.util.List;

import br.fiap.telegram.manager.BotManager;
import br.fiap.telegram.manager.SessionManager;
import br.fiap.telegram.model.Cliente;
import br.fiap.telegram.model.Lancamento;

public class ComandoTarifas implements Comando {


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

        tarifas(cliente);

    }

    private boolean clientePossuiConta(Cliente cliente) {
        return cliente.getConta() != null;
    }


    private void tarifas(Cliente cliente) throws Exception {

        List<Lancamento> tarifas = cliente.getConta().tarifas();

        if (tarifas != null && !tarifas.isEmpty()) {

            double somaTarifas = 0f;
            StringBuilder sb = new StringBuilder();

            for (Lancamento lc : tarifas) {

                sb.append(lc.getExtrato());
                sb.append("\n");

                somaTarifas += lc.getTarifa();
            }

            sb.append("\nTotal Tarifas: R$ " + somaTarifas);

            BotManager.enviarMensagem(cliente.getChatId(), sb.toString());

        } else {
            BotManager.enviarMensagem(cliente.getChatId(), "Você não possui tarifas!");
        }

        cliente.setMensagemAtual(null);
        cliente.setEstadoAtual(null);
        cliente.setComandoAtual(null);
        
        SessionManager.addClient(cliente);

        ComandoStart.mostrarMenu(cliente);

    }
}
